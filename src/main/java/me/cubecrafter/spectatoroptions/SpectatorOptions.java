package me.cubecrafter.spectatoroptions;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.server.ServerType;
import com.cryptomorin.xseries.XMaterial;
import me.cubecrafter.spectatoroptions.listeners.*;
import me.cubecrafter.spectatoroptions.utils.FileManager;
import me.cubecrafter.spectatoroptions.utils.Menu;
import me.cubecrafter.spectatoroptions.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public final class SpectatorOptions extends JavaPlugin {

    private static SpectatorOptions instance;
    public FileManager config;
    public ItemStack playAgainItem;
    public ItemStack optionsItem;
    public BedWars bw;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("BedWars1058") != null){
            bw = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
            if(bw.getServerType().equals(ServerType.BUNGEE)){
                getLogger().severe("BUNGEE server type detected! Only the spectator settings will work! Bungee support for Play Again will be added in the future!");
            }
            instance = this;
            getServer().getPluginManager().registerEvents(new ArenaJoinListener(), this);
            getServer().getPluginManager().registerEvents(new ArenaLeaveListener(), this);
            getServer().getPluginManager().registerEvents(new GameEndListener(), this);
            getServer().getPluginManager().registerEvents(new InteractListener(), this);
            getServer().getPluginManager().registerEvents(new DeathListener(), this);
            getServer().getPluginManager().registerEvents(new MenuListener(), this);
            config = new FileManager("config", "plugins/BedWars1058/Addons/SpectatorOptions");
        }else{
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void openMenu(Player p){
        Menu settings = new Menu(config.getYml().getInt("menu-size"), Utils.color(config.getYml().getString("menu-displayname")));
        settings.newItem(config.getYml().getString("items.no-speed.material"), config.getYml().getInt("items.no-speed.slot"), false, Utils.color(config.getYml().getString("items.no-speed.displayname")), Utils.color(config.getYml().getStringList("items.no-speed.lore")));
        settings.newItem(config.getYml().getString("items.speed-1.material"), config.getYml().getInt("items.speed-1.slot"), false, Utils.color(config.getYml().getString("items.speed-1.displayname")), Utils.color(config.getYml().getStringList("items.speed-1.lore")));
        settings.newItem(config.getYml().getString("items.speed-2.material"), config.getYml().getInt("items.speed-2.slot"), false, Utils.color(config.getYml().getString("items.speed-2.displayname")), Utils.color(config.getYml().getStringList("items.speed-2.lore")));
        settings.newItem(config.getYml().getString("items.speed-3.material"), config.getYml().getInt("items.speed-3.slot"), false, Utils.color(config.getYml().getString("items.speed-3.displayname")), Utils.color(config.getYml().getStringList("items.speed-3.lore")));
        settings.newItem(config.getYml().getString("items.speed-4.material"), config.getYml().getInt("items.speed-4.slot"), false, Utils.color(config.getYml().getString("items.speed-4.displayname")), Utils.color(config.getYml().getStringList("items.speed-4.lore")));
        settings.newItem(config.getYml().getString("items.night-vision.material"), config.getYml().getInt("items.night-vision.slot"), p.hasPotionEffect(PotionEffectType.NIGHT_VISION), p.hasPotionEffect(PotionEffectType.NIGHT_VISION) ? Utils.color(config.getYml().getString("items.night-vision.displayname-enabled")) : Utils.color(config.getYml().getString("items.night-vision.displayname-disabled")), p.hasPotionEffect(PotionEffectType.NIGHT_VISION) ? Utils.color(config.getYml().getStringList("items.night-vision.lore-enabled")) : Utils.color(config.getYml().getStringList("items.night-vision.lore-disabled")));
        settings.openMenu(p);
    }

    public void addPlayAgainItem(Player p){
        Bukkit.getScheduler().runTaskLater(this, () -> {
            playAgainItem = XMaterial.matchXMaterial(config.getYml().getString("play-again-item.material")).get().parseItem();
            ItemMeta meta = playAgainItem.getItemMeta();
            meta.setDisplayName(Utils.color(config.getYml().getString("play-again-item.displayname")));
            meta.setLore(Utils.color(config.getYml().getStringList("play-again-item.lore")));
            playAgainItem.setItemMeta(meta);
            p.getInventory().setItem(config.getYml().getInt("play-again-item.slot"), playAgainItem);
        }, 20L);
    }

    public void addOptionsItem(Player p){
        Bukkit.getScheduler().runTaskLater(this, () -> {
            optionsItem = XMaterial.matchXMaterial(config.getYml().getString("spectator-settings-item.material")).get().parseItem();
            ItemMeta meta = optionsItem.getItemMeta();
            meta.setDisplayName(Utils.color(config.getYml().getString("spectator-settings-item.displayname")));
            meta.setLore(Utils.color(config.getYml().getStringList("spectator-settings-item.lore")));
            optionsItem.setItemMeta(meta);
            p.getInventory().setItem(config.getYml().getInt("spectator-settings-item.slot"), optionsItem);
        }, 20L);
    }

    public void playAgain(Player p){
        String arenaGroup = bw.getArenaUtil().getArenaByPlayer(p).getGroup();
        bw.getArenaUtil().getArenaByPlayer(p).removePlayer(p, true);
        bw.getArenaUtil().joinRandomFromGroup(p, arenaGroup);
    }

    public static SpectatorOptions getInstance(){
        return instance;
    }

}
