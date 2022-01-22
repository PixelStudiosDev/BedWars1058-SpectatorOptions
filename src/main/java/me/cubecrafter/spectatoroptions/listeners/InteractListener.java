package me.cubecrafter.spectatoroptions.listeners;

import me.cubecrafter.spectatoroptions.SpectatorOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getItemInHand().equals(SpectatorOptions.getInstance().optionsItem)){
            SpectatorOptions.getInstance().openMenu(p);
        }else if(p.getItemInHand().equals(SpectatorOptions.getInstance().playAgainItem)){
            SpectatorOptions.getInstance().playAgain(p);
        }
    }

}
