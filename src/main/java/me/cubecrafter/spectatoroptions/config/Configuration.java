package me.cubecrafter.spectatoroptions.config;

import lombok.RequiredArgsConstructor;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Configuration {
    
    INVENTORY_ITEM("inventory-item"),
    INVENTORY_ITEM_SLOT("inventory-item.slot"),
    MENU_TITLE("menu.title"),
    MENU_ROWS("menu.rows"),
    MENU_FILLER("menu.filler"),
    MENU_FILLER_ENABLED("menu.filler.enabled"),
    MENU_FILLER_SLOTS("menu.filler.slots"),
    MENU_SPEED_OFF("menu.items.speed-off"),
    MENU_SPEED_OFF_SLOT("menu.items.speed-off.slot"),
    MENU_SPEED_ONE("menu.items.speed-one"),
    MENU_SPEED_ONE_SLOT("menu.items.speed-one.slot"),
    MENU_SPEED_TWO("menu.items.speed-two"),
    MENU_SPEED_TWO_SLOT("menu.items.speed-two.slot"),
    MENU_SPEED_THREE("menu.items.speed-three"),
    MENU_SPEED_THREE_SLOT("menu.items.speed-three.slot"),
    MENU_SPEED_FOUR("menu.items.speed-four"),
    MENU_SPEED_FOUR_SLOT("menu.items.speed-four.slot"),
    MENU_AUTO_TELEPORT_ENABLED("menu.items.auto-teleport.enabled"),
    MENU_AUTO_TELEPORT_DISABLED("menu.items.auto-teleport.disabled"),
    MENU_AUTO_TELEPORT_SLOT("menu.items.auto-teleport.slot"),
    MENU_NIGHT_VISION_ENABLED("menu.items.night-vision.enabled"),
    MENU_NIGHT_VISION_DISABLED("menu.items.night-vision.disabled"),
    MENU_NIGHT_VISION_SLOT("menu.items.night-vision.slot"),
    MENU_FIRST_PERSON_ENABLED("menu.items.first-person.enabled"),
    MENU_FIRST_PERSON_DISABLED("menu.items.first-person.disabled"),
    MENU_FIRST_PERSON_SLOT("menu.items.first-person.slot"),
    MENU_SHOW_SPECTATORS_ENABLED("menu.items.show-spectators.enabled"),
    MENU_SHOW_SPECTATORS_DISABLED("menu.items.show-spectators.disabled"),
    MENU_SHOW_SPECTATORS_SLOT("menu.items.show-spectators.slot"),
    MESSAGES_SPEED_ENABLED("messages.speed-enabled"),
    MESSAGES_SPEED_DISABLED("messages.speed-disabled"),
    MESSAGES_AUTO_TELEPORT_ENABLED("messages.auto-teleport-enabled"),
    MESSAGES_AUTO_TELEPORT_DISABLED("messages.auto-teleport-disabled"),
    MESSAGES_NIGHT_VISION_ENABLED("messages.night-vision-enabled"),
    MESSAGES_NIGHT_VISION_DISABLED("messages.night-vision-disabled"),
    MESSAGES_FIRST_PERSON_ENABLED("messages.first-person-enabled"),
    MESSAGES_FIRST_PERSON_DISABLED("messages.first-person-disabled"),
    MESSAGES_SHOW_SPECTATORS_ENABLED("messages.show-spectators-enabled"),
    MESSAGES_SHOW_SPECTATORS_DISABLED("messages.show-spectators-disabled"),
    MESSAGES_TARGET_FORMAT("messages.target-format"),
    MESSAGES_TARGET_LOST("messages.target-lost");

    private final String path;

    public String getAsString() {
        return SpectatorOptions.getInstance().getFileManager().getConfig().getString(path);
    }

    public int getAsInt() {
        return SpectatorOptions.getInstance().getFileManager().getConfig().getInt(path);
    }

    public boolean getAsBoolean() {
        return SpectatorOptions.getInstance().getFileManager().getConfig().getBoolean(path);
    }

    public ConfigurationSection getAsConfigSection() {
        return SpectatorOptions.getInstance().getFileManager().getConfig().getConfigurationSection(path);
    }

    public List<Integer> getAsIntegerList() {
        return Arrays.stream(SpectatorOptions.getInstance().getFileManager().getConfig().getString(path).split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
    
}
