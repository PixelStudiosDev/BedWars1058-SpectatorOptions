package me.cubecrafter.spectatoroptions;

import com.andrei1058.bedwars.api.BedWars;
import lombok.Getter;
import me.cubecrafter.spectatoroptions.config.FileManager;
import me.cubecrafter.spectatoroptions.data.PlayerManager;
import me.cubecrafter.spectatoroptions.listeners.ArenaListener;
import me.cubecrafter.spectatoroptions.listeners.InventoryListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SpectatorOptions extends JavaPlugin {

    @Getter
    private static SpectatorOptions instance;
    private PlayerManager playerManager;
    private FileManager fileManager;
    private BedWars bedWars;

    @Override
    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        bedWars = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        playerManager = new PlayerManager();
        getServer().getPluginManager().registerEvents(new ArenaListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        new Metrics(this, 14041);
    }

    @Override
    public void onDisable() {
        getPlayerManager().getTeleportTask().getTask().cancel();
    }

}
