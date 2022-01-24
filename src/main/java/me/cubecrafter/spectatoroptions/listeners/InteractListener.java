package me.cubecrafter.spectatoroptions.listeners;

import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.menus.PlayAgainMenu;
import me.cubecrafter.spectatoroptions.menus.SpectatorSettingsMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getItemInHand().equals(SpectatorOptions.getInstance().optionsItem)){
            SpectatorSettingsMenu spectatorSettingsMenu = new SpectatorSettingsMenu(p);
        }else if(p.getItemInHand().equals(SpectatorOptions.getInstance().playAgainItem)){
            PlayAgainMenu playAgainMenu = new PlayAgainMenu(p);
        }
    }

}
