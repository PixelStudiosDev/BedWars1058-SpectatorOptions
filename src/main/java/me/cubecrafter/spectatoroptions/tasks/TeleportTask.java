package me.cubecrafter.spectatoroptions.tasks;

import com.cryptomorin.xseries.messages.ActionBar;
import lombok.Getter;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.config.Configuration;
import me.cubecrafter.spectatoroptions.data.PlayerData;
import me.cubecrafter.spectatoroptions.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TeleportTask implements Runnable {

    @Getter
    private final BukkitTask task;

    public TeleportTask() {
        task = Bukkit.getServer().getScheduler().runTaskTimer(SpectatorOptions.getInstance(), this, 0, 10L);
    }

    @Override
    public void run() {
        for (PlayerData data : SpectatorOptions.getInstance().getPlayerManager().getData().values()) {
            if (data.getTarget() == null || data.getPlayer().getSpectatorTarget() != null) continue;
            Player player = data.getPlayer();
            Player target = data.getTarget();
            if (!target.isOnline() || !player.getWorld().equals(target.getWorld())) {
                data.setTarget(null);
                player.setGameMode(GameMode.ADVENTURE);
                ActionBar.sendActionBar(data.getPlayer(), TextUtil.color(Configuration.MESSAGES_TARGET_LOST.getAsString()));
                continue;
            }
            double distance = player.getLocation().distance(target.getLocation());
            if (distance > 10 && data.isAutoTeleport()) {
                player.teleport(target);
            }
            ActionBar.sendActionBar(player, TextUtil.color(Configuration.MESSAGES_TARGET_FORMAT.getAsString().replace("{target}", target.getName()).replace("{distance}", String.format("%.1f", distance))));
        }
    }

}
