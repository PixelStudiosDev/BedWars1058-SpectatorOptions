package me.cubecrafter.spectatoroptions.commands;

import me.cubecrafter.spectatoroptions.menus.PlayAgainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            PlayAgainMenu playAgainMenu = new PlayAgainMenu(p);
        }
        return true;
    }
}