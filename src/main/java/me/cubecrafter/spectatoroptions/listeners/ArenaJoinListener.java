package me.cubecrafter.spectatoroptions.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaJoinListener implements Listener {

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent e){
        if(e.isSpectator() && SpectatorOptions.getInstance().config.getYml().getBoolean("spectator-settings-item.enabled")){
            SpectatorOptions.getInstance().addOptionsItem(e.getPlayer());
        }
    }

}
