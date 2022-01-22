package me.cubecrafter.spectatoroptions.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DeathListener implements Listener {

    @EventHandler
    public void onFinalKill(PlayerKillEvent e){
        YamlConfiguration config = SpectatorOptions.getInstance().config.getYml();
        if(e.getCause().isFinalKill()){
            if(config.getBoolean("play-again-item.enabled")){
            SpectatorOptions.getInstance().addPlayAgainItem(e.getVictim());
            }
            if(config.getBoolean("spectator-settings-item.enabled")) {
                SpectatorOptions.getInstance().addOptionsItem(e.getVictim());
            }
        }
    }

}
