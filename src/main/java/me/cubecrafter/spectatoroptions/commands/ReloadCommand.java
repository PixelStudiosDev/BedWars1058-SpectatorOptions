package me.cubecrafter.spectatoroptions.commands;

import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.utils.Utils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends SubCommand {
    public ReloadCommand(ParentCommand parent, String name) {
        super(parent, name);
        showInList(true);
        setDisplayInfo(new TextComponent("§6 ▪ §7/bw " + getSubCommandName() + " §8- §ereload configuration"));
        setPriority(20);
        setArenaSetupCommand(false);
    }

    @Override
    public boolean execute(String[] strings, CommandSender commandSender) {
        SpectatorOptions.getInstance().config.load();
        commandSender.sendMessage(Utils.color(SpectatorOptions.getInstance().config.getYml().getString("messages.config-reloaded")));
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
