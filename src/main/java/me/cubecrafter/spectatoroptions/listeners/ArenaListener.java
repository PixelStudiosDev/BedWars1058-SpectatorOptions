package me.cubecrafter.spectatoroptions.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.spectator.SpectatorFirstPersonEnterEvent;
import com.andrei1058.bedwars.api.events.spectator.SpectatorTeleportToPlayerEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.cryptomorin.xseries.messages.ActionBar;
import lombok.RequiredArgsConstructor;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.config.Configuration;
import me.cubecrafter.spectatoroptions.data.PlayerData;
import me.cubecrafter.spectatoroptions.menus.SettingsMenu;
import me.cubecrafter.spectatoroptions.utils.ItemBuilder;
import me.cubecrafter.spectatoroptions.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class ArenaListener implements Listener {

    private final SpectatorOptions plugin;

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent e) {
        if (e.isSpectator()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> giveItem(e.getPlayer()), 10L);
        }
    }

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent e) {
        Player player = e.getPlayer();
        if (e.isSpectator()) {
            player.setFlySpeed(0.1f);
            player.removePotionEffect(PotionEffectType.SPEED);
            PlayerData data = plugin.getPlayerManager().getData(player);
            data.setTarget(null);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        for (Player player : e.getArena().getSpectators()) {
            PlayerData data = plugin.getPlayerManager().getData(player);
            data.setTarget(null);
        }
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        Player player = e.getVictim();
        PlayerData data = plugin.getPlayerManager().getData(player);
        for (Player spectator : e.getArena().getSpectators()) {
            if (!data.isShowSpectators()) {
                plugin.getBedWars().getVersionSupport().spigotHidePlayer(spectator, player);
            }
            PlayerData spectatorData = plugin.getPlayerManager().getData(spectator);
            if (!spectatorData.isShowSpectators() && e.getCause().isFinalKill()) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.getBedWars().getVersionSupport().spigotHidePlayer(player, spectator), 10L);
            }
            if (spectatorData.getTarget() == null || !spectatorData.getTarget().equals(player)) continue;
            spectatorData.setTarget(null);
            spectator.setGameMode(GameMode.ADVENTURE);
            ActionBar.sendActionBar(spectatorData.getPlayer(), TextUtil.color(Configuration.MESSAGES_TARGET_LOST.getAsString()));
        }
        if (e.getCause().isFinalKill()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> giveItem(player), 10L);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        Player player = e.getPlayer();
        IArena arena = plugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
        if (arena == null) return;
        if (ItemBuilder.getTag(e.getItem(), "spectator").equals("settings-item")) {
            new SettingsMenu(player, arena).openMenu();
        }
    }

    @EventHandler
    public void onSpectatorTeleport(SpectatorTeleportToPlayerEvent e) {
        Player player = e.getSpectator();
        PlayerData data = plugin.getPlayerManager().getData(player);
        data.setTarget(e.getTarget());
        if (!data.isFirstPerson()) return;
        e.setCancelled(true);
        SpectatorFirstPersonEnterEvent event = new SpectatorFirstPersonEnterEvent(player, e.getTarget(), e.getArena(), p -> Language.getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE), p -> Language.getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE));
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        player.teleport(e.getTarget());
        player.getInventory().setHeldItemSlot(5);
        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(e.getTarget());
        plugin.getBedWars().getVersionSupport().sendTitle(player, event.getTitle().apply(player).replace("{playername}", player.getName()).replace("{player}", e.getTarget().getDisplayName()), event.getSubTitle().apply(player).replace("{player}", e.getTarget().getDisplayName()), event.getFadeIn(), event.getStay(), event.getFadeOut());
    }

    @EventHandler
    public void onFirstPersonEnter(SpectatorFirstPersonEnterEvent e) {
        Player player = e.getSpectator();
        PlayerData data = plugin.getPlayerManager().getData(player);
        data.setTarget(e.getTarget());
    }

    public void giveItem(Player player) {
        player.getInventory().setItem(Configuration.INVENTORY_ITEM_SLOT.getAsInt(), ItemBuilder.fromConfig(Configuration.INVENTORY_ITEM.getAsConfigSection()).setTag("spectator", "settings-item").build());
    }

}
