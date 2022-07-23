package me.cubecrafter.spectatoroptions.data;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class PlayerData {

    private final Player player;
    private Player target;
    private boolean autoTeleport;
    private boolean firstPerson;
    private boolean showSpectators;

}
