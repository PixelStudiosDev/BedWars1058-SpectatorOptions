package me.cubecrafter.spectatoroptions.listeners;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        YamlConfiguration config = SpectatorOptions.getInstance().config.getYml();
        BedWars.ArenaUtil arenaUtil = SpectatorOptions.getInstance().bw.getArenaUtil();
        Player p = (Player) e.getWhoClicked();
        IArena playerArena = arenaUtil.getArenaByPlayer(p);
        if(e.getView().getTitle().equals(Utils.color(config.getString("settings-menu-displayname")))){
            e.setCancelled(true);
            int slot = e.getRawSlot();
            if(slot == config.getInt("settings-items.no-speed.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-disabled")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.setFlySpeed(0.1f);
            }else if(slot == config.getInt("settings-items.speed-1.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "I")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
                p.setFlySpeed(0.2f);
            }else if(slot == config.getInt("settings-items.speed-2.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "II")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                p.setFlySpeed(0.4f);
            }else if(slot == config.getInt("settings-items.speed-3.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "III")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
                p.setFlySpeed(0.7f);
            }else if(slot == config.getInt("settings-items.speed-4.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "IV")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
                p.setFlySpeed(1.0f);
            }else if(slot == config.getInt("settings-items.night-vision.slot")){
                if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                    p.sendMessage(Utils.color(config.getString("messages.night-vision-enabled")));
                    setItem(e.getInventory(), config.getString("settings-items.night-vision.material"), config.getInt("settings-items.night-vision.slot"), true, Utils.color(config.getString("settings-items.night-vision.displayname-enabled")), Utils.color(config.getStringList("settings-items.night-vision.lore-enabled")));
                }else{
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    p.sendMessage(Utils.color(config.getString("messages.night-vision-disabled")));
                    setItem(e.getInventory(), config.getString("settings-items.night-vision.material"), config.getInt("settings-items.night-vision.slot"), false, Utils.color(config.getString("settings-items.night-vision.displayname-disabled")), Utils.color(config.getStringList("settings-items.night-vision.lore-disabled")));
                    }
                }
            }
        if(e.getView().getTitle().equals(Utils.color(config.getString("playagain-menu-displayname")).replace("{group}", arenaUtil.getArenaByPlayer(p).getGroup()))){
            e.setCancelled(true);
            NBTItem nbtItem = new NBTItem(e.getCurrentItem());
            if(arenaUtil.getArenas().contains(arenaUtil.getArenaByName(nbtItem.getString("id")))){
                arenaUtil.getArenaByPlayer(p).removePlayer(p, true);
                arenaUtil.getArenaByName(nbtItem.getString("id")).addPlayer(p, false);
            }else if(nbtItem.getString("id").equals("random-join")){
                arenaUtil.getArenaByPlayer(p).removePlayer(p, true);
                arenaUtil.joinRandomFromGroup(p, playerArena.getGroup());
            }
        }
    }

    public void setItem(Inventory inv, String material, int slot, boolean glow, String name, List<String> lore){
        ItemStack item = XMaterial.matchXMaterial(material).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(glow){
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        inv.setItem(slot, item);
    }

}
