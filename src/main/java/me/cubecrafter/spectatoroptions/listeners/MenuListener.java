package me.cubecrafter.spectatoroptions.listeners;

import com.cryptomorin.xseries.XMaterial;
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
        if(e.getView().getTitle().equals(Utils.color(config.getString("menu-displayname")))){
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            int slot = e.getRawSlot();
            if(slot == config.getInt("items.no-speed.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-disabled")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.setFlySpeed(0.1f);
            }else if(slot == config.getInt("items.speed-1.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "I")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
                p.setFlySpeed(0.2f);
            }else if(slot == config.getInt("items.speed-2.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "II")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                p.setFlySpeed(0.4f);
            }else if(slot == config.getInt("items.speed-3.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "III")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
                p.setFlySpeed(0.7f);
            }else if(slot == config.getInt("items.speed-4.slot")){
                p.sendMessage(Utils.color(config.getString("messages.speed-enabled").replace("{tier}", "IV")));
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
                p.setFlySpeed(1.0f);
            }else if(slot == config.getInt("items.night-vision.slot")){
                if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                    p.sendMessage(Utils.color(config.getString("messages.night-vision-enabled")));
                    setItem(e.getInventory(), config.getString("items.night-vision.material"), config.getInt("items.night-vision.slot"), true, Utils.color(config.getString("items.night-vision.displayname-enabled")), Utils.color(config.getStringList("items.night-vision.lore-enabled")));
                }else{
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    p.sendMessage(Utils.color(config.getString("messages.night-vision-disabled")));
                    setItem(e.getInventory(), config.getString("items.night-vision.material"), config.getInt("items.night-vision.slot"), false, Utils.color(config.getString("items.night-vision.displayname-disabled")), Utils.color(config.getStringList("items.night-vision.lore-disabled")));
                    }
                }
            }
        }

    public void setItem(Inventory inv, String material, int slot, boolean glow, String name, List<String> lore){
        ItemStack item = XMaterial.matchXMaterial(material).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(glow){
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        inv.setItem(slot, item);
    }

}
