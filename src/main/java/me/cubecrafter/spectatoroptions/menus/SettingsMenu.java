package me.cubecrafter.spectatoroptions.menus;

import com.andrei1058.bedwars.api.arena.IArena;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.config.Configuration;
import me.cubecrafter.spectatoroptions.data.PlayerData;
import me.cubecrafter.spectatoroptions.utils.ItemBuilder;
import me.cubecrafter.spectatoroptions.utils.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsMenu extends Menu {

    private final PlayerData data;
    private final IArena arena;

    public SettingsMenu(Player player, IArena arena) {
        super(player);
        this.arena = arena;
        data = SpectatorOptions.getInstance().getPlayerManager().getData(player);
    }

    @Override
    public String getTitle() {
        return Configuration.MENU_TITLE.getAsString();
    }

    @Override
    public int getRows() {
        return Configuration.MENU_ROWS.getAsInt();
    }

    @Override
    public Map<Integer, MenuItem> getItems() {
        Map<Integer, MenuItem> items = new HashMap<>();
        items.put(Configuration.MENU_SPEED_OFF_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SPEED_OFF.getAsConfigSection()).build()).addAction(e -> {
            player.removePotionEffect(PotionEffectType.SPEED);
            player.setFlySpeed(0.1f);
            TextUtil.sendMessage(player, Configuration.MESSAGES_SPEED_DISABLED.getAsString());
            closeMenu();
        }));
        items.put(Configuration.MENU_SPEED_ONE_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SPEED_ONE.getAsConfigSection()).build()).addAction(e -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
            player.setFlySpeed(0.2f);
            TextUtil.sendMessage(player, Configuration.MESSAGES_SPEED_ENABLED.getAsString().replace("{tier}", "I"));
            closeMenu();
        }));
        items.put(Configuration.MENU_SPEED_TWO_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SPEED_TWO.getAsConfigSection()).build()).addAction(e -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
            player.setFlySpeed(0.4f);
            TextUtil.sendMessage(player, Configuration.MESSAGES_SPEED_ENABLED.getAsString().replace("{tier}", "II"));
            closeMenu();
        }));
        items.put(Configuration.MENU_SPEED_THREE_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SPEED_THREE.getAsConfigSection()).build()).addAction(e -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));
            player.setFlySpeed(0.7f);
            TextUtil.sendMessage(player, Configuration.MESSAGES_SPEED_ENABLED.getAsString().replace("{tier}", "III"));
            closeMenu();
        }));
        items.put(Configuration.MENU_SPEED_FOUR_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SPEED_FOUR.getAsConfigSection()).build()).addAction(e -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
            player.setFlySpeed(1.0f);
            TextUtil.sendMessage(player, Configuration.MESSAGES_SPEED_ENABLED.getAsString().replace("{tier}", "IV"));
            closeMenu();
        }));
        if (data.isAutoTeleport()) {
            items.put(Configuration.MENU_AUTO_TELEPORT_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_AUTO_TELEPORT_ENABLED.getAsConfigSection()).build()).addAction(e -> {
                data.setAutoTeleport(false);
                TextUtil.sendMessage(player, Configuration.MESSAGES_AUTO_TELEPORT_DISABLED.getAsString());
                closeMenu();
            }));
        } else {
            items.put(Configuration.MENU_AUTO_TELEPORT_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_AUTO_TELEPORT_DISABLED.getAsConfigSection()).build()).addAction(e -> {
                data.setAutoTeleport(true);
                TextUtil.sendMessage(player, Configuration.MESSAGES_AUTO_TELEPORT_ENABLED.getAsString());
                closeMenu();
            }));
        }
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            items.put(Configuration.MENU_NIGHT_VISION_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_NIGHT_VISION_ENABLED.getAsConfigSection()).build()).addAction(e -> {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                TextUtil.sendMessage(player, Configuration.MESSAGES_NIGHT_VISION_DISABLED.getAsString());
                closeMenu();
            }));
        } else {
            items.put(Configuration.MENU_NIGHT_VISION_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_NIGHT_VISION_DISABLED.getAsConfigSection()).build()).addAction(e -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                TextUtil.sendMessage(player, Configuration.MESSAGES_NIGHT_VISION_ENABLED.getAsString());
                closeMenu();
            }));
        }
        if (data.isFirstPerson()) {
            items.put(Configuration.MENU_FIRST_PERSON_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_FIRST_PERSON_ENABLED.getAsConfigSection()).build()).addAction(e -> {
                data.setFirstPerson(false);
                TextUtil.sendMessage(player, Configuration.MESSAGES_FIRST_PERSON_DISABLED.getAsString());
                closeMenu();
            }));
        } else {
            items.put(Configuration.MENU_FIRST_PERSON_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_FIRST_PERSON_DISABLED.getAsConfigSection()).build()).addAction(e -> {
                data.setFirstPerson(true);
                TextUtil.sendMessage(player, Configuration.MESSAGES_FIRST_PERSON_ENABLED.getAsString());
                closeMenu();
            }));
        }
        if (data.isShowSpectators()) {
            items.put(Configuration.MENU_SHOW_SPECTATORS_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SHOW_SPECTATORS_ENABLED.getAsConfigSection()).build()).addAction(e -> {
                data.setShowSpectators(false);
                for (Player spectator : arena.getSpectators()) {
                    SpectatorOptions.getInstance().getBedWars().getVersionSupport().spigotHidePlayer(spectator, player);
                }
                TextUtil.sendMessage(player, Configuration.MESSAGES_SHOW_SPECTATORS_DISABLED.getAsString());
                closeMenu();
            }));
        } else {
            items.put(Configuration.MENU_SHOW_SPECTATORS_SLOT.getAsInt(), new MenuItem(ItemBuilder.fromConfig(Configuration.MENU_SHOW_SPECTATORS_DISABLED.getAsConfigSection()).build()).addAction(e -> {
                data.setShowSpectators(true);
                for (Player spectator : arena.getSpectators()) {
                    SpectatorOptions.getInstance().getBedWars().getVersionSupport().spigotShowPlayer(spectator, player);
                }
                TextUtil.sendMessage(player, Configuration.MESSAGES_SHOW_SPECTATORS_ENABLED.getAsString());
                closeMenu();
            }));
        }
        return items;
    }

}
