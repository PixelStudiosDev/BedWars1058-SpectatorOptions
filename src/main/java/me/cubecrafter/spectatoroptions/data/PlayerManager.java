package me.cubecrafter.spectatoroptions.data;

import lombok.Getter;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.tasks.TeleportTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

@Getter
public class PlayerManager implements Listener {

    private final Map<UUID, PlayerData> data = new HashMap<>();
    private final TeleportTask teleportTask;

    public PlayerManager() {
        Bukkit.getServer().getPluginManager().registerEvents(this, SpectatorOptions.getInstance());
        teleportTask = new TeleportTask();
    }

    public PlayerData getData(Player player) {
        if (!data.containsKey(player.getUniqueId())) {
            PlayerData playerData = new PlayerData(player);
            playerData.setShowSpectators(true);
            playerData.setAutoTeleport(true);
            data.put(player.getUniqueId(), playerData);
        }
        return data.get(player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        data.remove(e.getPlayer().getUniqueId());
    }

}
