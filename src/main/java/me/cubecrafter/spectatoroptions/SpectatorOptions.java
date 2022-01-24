package me.cubecrafter.spectatoroptions;

import com.andrei1058.bedwars.api.BedWars;
import com.cryptomorin.xseries.XMaterial;
import me.cubecrafter.spectatoroptions.commands.TestCommand;
import me.cubecrafter.spectatoroptions.listeners.*;
import me.cubecrafter.spectatoroptions.utils.FileManager;
import me.cubecrafter.spectatoroptions.utils.Metrics;
import me.cubecrafter.spectatoroptions.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
