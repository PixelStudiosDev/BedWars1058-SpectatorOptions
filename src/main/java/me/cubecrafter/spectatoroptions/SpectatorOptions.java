package me.cubecrafter.spectatoroptions;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.server.ServerType;
import com.cryptomorin.xseries.XMaterial;
import me.cubecrafter.spectatoroptions.commands.TestCommand;
import me.cubecrafter.spectatoroptions.listeners.*;
import me.cubecrafter.spectatoroptions.utils.FileManager;
import me.cubecrafter.spectatoroptions.utils.Menu;
import me.cubecrafter.spectatoroptions.utils.Metrics;
import me.cubecrafter.spectatoroptions.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

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
            instance = this;
            config = new FileManager("config", "plugins/BedWars1058/Addons/SpectatorOptions");
            getServer().getPluginManager().registerEvents(new ArenaJoinListener(), this);
            getServer().getPluginManager().registerEvents(new ArenaLeaveListener(), this);
            getServer().getPluginManager().registerEvents(new GameEndListener(), this);
            getServer().getPluginManager().registerEvents(new InteractListener(), this);
            getServer().getPluginManager().registerEvents(new DeathListener(), this);
            getServer().getPluginManager().registerEvents(new MenuListener(), this);
            getCommand("test").setExecutor(new TestCommand());
            int pluginId = 14041;
            Metrics metrics = new Metrics(this, pluginId);
        }else{
            getLogger().severe("BedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void openMenu(Player p){
        Menu settings = new Menu(config.getYml().getInt("settings-menu-size"), Utils.color(config.getYml().getString("settings-menu-displayname")));
        settings.setItem(config.getYml().getString("settings-items.no-speed.material"), config.getYml().getInt("settings-items.no-speed.slot"), false, Utils.color(config.getYml().getString("settings-items.no-speed.displayname")), Utils.color(config.getYml().getStringList("settings-items.no-speed.lore")));
        settings.setItem(config.getYml().getString("settings-items.speed-1.material"), config.getYml().getInt("settings-items.speed-1.slot"), false, Utils.color(config.getYml().getString("settings-items.speed-1.displayname")), Utils.color(config.getYml().getStringList("settings-items.speed-1.lore")));
        settings.setItem(config.getYml().getString("settings-items.speed-2.material"), config.getYml().getInt("settings-items.speed-2.slot"), false, Utils.color(config.getYml().getString("settings-items.speed-2.displayname")), Utils.color(config.getYml().getStringList("settings-items.speed-2.lore")));
        settings.setItem(config.getYml().getString("settings-items.speed-3.material"), config.getYml().getInt("settings-items.speed-3.slot"), false, Utils.color(config.getYml().getString("settings-items.speed-3.displayname")), Utils.color(config.getYml().getStringList("settings-items.speed-3.lore")));
        settings.setItem(config.getYml().getString("settings-items.speed-4.material"), config.getYml().getInt("settings-items.speed-4.slot"), false, Utils.color(config.getYml().getString("settings-items.speed-4.displayname")), Utils.color(config.getYml().getStringList("settings-items.speed-4.lore")));
        settings.setItem(config.getYml().getString("settings-items.night-vision.material"), config.getYml().getInt("settings-items.night-vision.slot"), p.hasPotionEffect(PotionEffectType.NIGHT_VISION), p.hasPotionEffect(PotionEffectType.NIGHT_VISION) ? Utils.color(config.getYml().getString("settings-items.night-vision.displayname-enabled")) : Utils.color(config.getYml().getString("settings-items.night-vision.displayname-disabled")), p.hasPotionEffect(PotionEffectType.NIGHT_VISION) ? Utils.color(config.getYml().getStringList("settings-items.night-vision.lore-enabled")) : Utils.color(config.getYml().getStringList("settings-items.night-vision.lore-disabled")));
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

    public static SpectatorOptions getInstance(){
        return instance;
    }

}
