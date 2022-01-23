package me.cubecrafter.spectatoroptions.listeners;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class GameEndListener implements Listener {

    @EventHandler
    public void onGameEnd(GameEndEvent e){

        String arenaGroup = e.getArena().getGroup();
        BedWars.ArenaUtil arenaUtil = SpectatorOptions.getInstance().bw.getArenaUtil();
        YamlConfiguration config = SpectatorOptions.getInstance().config.getYml();

        Bukkit.getScheduler().runTaskLater(SpectatorOptions.getInstance(), () -> {

            if(config.getBoolean("spectators-auto-playagain-on-game-end")){
                for(Player p : e.getArena().getSpectators()){
                    e.getArena().removePlayer(p, true);
                    arenaUtil.joinRandomFromGroup(p, arenaGroup);
                }
            }

            if(config.getBoolean("winners-auto-playagain-on-game-end")){
                for(UUID uuid : e.getWinners()){
                    Player p = Bukkit.getPlayer(uuid);
                    e.getArena().removePlayer(p, true);
                    arenaUtil.joinRandomFromGroup(p, arenaGroup);
                }

            }
        }, 80L);
    }

}
