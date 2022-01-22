package me.cubecrafter.spectatoroptions.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(List<String> list){
        List<String> color = new ArrayList<>();
        list.forEach(s -> color.add(color(s)));
        return color;
    }

}
