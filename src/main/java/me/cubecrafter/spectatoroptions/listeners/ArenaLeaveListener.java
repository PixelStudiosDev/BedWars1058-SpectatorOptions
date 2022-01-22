package me.cubecrafter.spectatoroptions.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class ArenaLeaveListener implements Listener {

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent e){
        if(e.isSpectator()){
            e.getPlayer().setFlySpeed(0.1f);
            e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

}
