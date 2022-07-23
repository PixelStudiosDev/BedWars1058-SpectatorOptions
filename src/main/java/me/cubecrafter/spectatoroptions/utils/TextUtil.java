package me.cubecrafter.spectatoroptions.utils;

import lombok.experimental.UtilityClass;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
public class TextUtil {

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public List<String> color(List<String> list){
        list.replaceAll(TextUtil::color);
        return list;
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(color(message));
    }

    public void info(String message) {
        SpectatorOptions.getInstance().getLogger().info(message);
    }

    public TextComponent buildTextComponent(String text, String click, String hover, ClickEvent.Action action) {
        TextComponent component = new TextComponent(color(text));
        component.setClickEvent(new ClickEvent(action, click));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hover)).create()));
        return component;
    }

}
