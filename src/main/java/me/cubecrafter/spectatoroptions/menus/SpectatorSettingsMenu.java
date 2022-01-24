package me.cubecrafter.spectatoroptions.menus;

import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.utils.Menu;
import me.cubecrafter.spectatoroptions.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SpectatorSettingsMenu {
    
    public SpectatorSettingsMenu(Player p){
        YamlConfiguration config = SpectatorOptions.getInstance().config.getYml();
        Menu settings = new Menu(config.getInt("settings-menu-size"), Utils.color(config.getString("settings-menu-displayname")));
        settings.setItem(config.getString("settings-items.no-speed.material"), config.getInt("settings-items.no-speed.slot"), false, Utils.color(config.getString("settings-items.no-speed.displayname")), Utils.color(config.getStringList("settings-items.no-speed.lore")));
        settings.setItem(config.getString("settings-items.speed-1.material"), config.getInt("settings-items.speed-1.slot"), false, Utils.color(config.getString("settings-items.speed-1.displayname")), Utils.color(config.getStringList("settings-items.speed-1.lore")));
        settings.setItem(config.getString("settings-items.speed-2.material"), config.getInt("settings-items.speed-2.slot"), false, Utils.color(config.getString("settings-items.speed-2.displayname")), Utils.color(config.getStringList("settings-items.speed-2.lore")));
        settings.setItem(config.getString("settings-items.speed-3.material"), config.getInt("settings-items.speed-3.slot"), false, Utils.color(config.getString("settings-items.speed-3.displayname")), Utils.color(config.getStringList("settings-items.speed-3.lore")));
        settings.setItem(config.getString("settings-items.speed-4.material"), config.getInt("settings-items.speed-4.slot"), false, Utils.color(config.getString("settings-items.speed-4.displayname")), Utils.color(config.getStringList("settings-items.speed-4.lore")));
        settings.setItem(config.getString("settings-items.night-vision.material"), config.getInt("settings-items.night-vision.slot"), p.hasPotionEffect(PotionEffectType.NIGHT_VISION), p.hasPotionEffect(PotionEffectType.NIGHT_VISION) ? Utils.color(config.getString("settings-items.night-vision.displayname-enabled")) : Utils.color(config.getString("settings-items.night-vision.displayname-disabled")), p.hasPotionEffect(PotionEffectType.NIGHT_VISION) ? Utils.color(config.getStringList("settings-items.night-vision.lore-enabled")) : Utils.color(config.getStringList("settings-items.night-vision.lore-disabled")));
        settings.openMenu(p);
    }
    
}
