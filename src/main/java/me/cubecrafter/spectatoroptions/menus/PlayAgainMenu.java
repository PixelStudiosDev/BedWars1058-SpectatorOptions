package me.cubecrafter.spectatoroptions.menus;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import me.cubecrafter.spectatoroptions.SpectatorOptions;
import me.cubecrafter.spectatoroptions.utils.Menu;
import me.cubecrafter.spectatoroptions.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Iterator;

public class PlayAgainMenu {

    public PlayAgainMenu(Player p){
        YamlConfiguration config = SpectatorOptions.getInstance().config.getYml();
        BedWars.ArenaUtil arenaUtil = SpectatorOptions.getInstance().bw.getArenaUtil();
        Menu playAgain = new Menu(config.getInt("playagain-menu-size"), Utils.color(config.getString("playagain-menu-displayname").replace("{group}", arenaUtil.getArenaByPlayer(p).getGroup())));
        Iterator<String> it = Arrays.stream(config.getString("playagain-menu-items.arena-item.slots").split(",")).iterator();
        for(IArena arena : SpectatorOptions.getInstance().bw.getArenaUtil().getArenas()){
            if(arena.getGroup().equals(arenaUtil.getArenaByPlayer(p).getGroup()) && arena.getStatus().equals(GameState.waiting) || arena.getStatus().equals(GameState.starting)){
                if(it.hasNext()){
                    playAgain.setItem(config.getString("playagain-menu-items.arena-item.material"), Integer.parseInt(it.next()), arena.getStatus().equals(GameState.starting), Utils.format(config.getString("playagain-menu-items.arena-item.displayname"), arena), Utils.format(config.getStringList("playagain-menu-items.arena-item.lore"), arena), arena.getArenaName());
                }
            }
        }
        if(config.getBoolean("playagain-menu-items.filler-item.enabled")){
            Iterator<String> it2 = Arrays.stream(config.getString("playagain-menu-items.filler-item.slots").split(",")).iterator();
            while(it2.hasNext()){
                playAgain.setItem(config.getString("playagain-menu-items.filler-item.material"), Integer.parseInt(it2.next()), config.getBoolean("playagain-menu-items.filler-item.enchanted"), Utils.color(config.getString("playagain-menu-items.filler-item.displayname")), Utils.color(config.getStringList("playagain-menu-items.filler-item.lore")));
            }
        }
        playAgain.setItem(config.getString("playagain-menu-items.random-join-item.material"), config.getInt("playagain-menu-items.random-join-item.slot"), config.getBoolean("playagain-menu-items.random-join-item.enchanted"), Utils.color(config.getString("playagain-menu-items.random-join-item.displayname")), Utils.color(config.getStringList("playagain-menu-items.random-join-item.lore")), "random-join");
        playAgain.openMenu(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!p.getOpenInventory().getTitle().equals(Utils.color(config.getString("playagain-menu-displayname")).replace("{group}", arenaUtil.getArenaByPlayer(p).getGroup()))){
                    cancel();
                }
                Iterator<String> it2 = Arrays.stream(config.getString("playagain-menu-items.arena-item.slots").split(",")).iterator();
                for(IArena arena : SpectatorOptions.getInstance().bw.getArenaUtil().getArenas()){
                    if(arena.getGroup().equals(arenaUtil.getArenaByPlayer(p).getGroup()) && arena.getStatus().equals(GameState.waiting) || arena.getStatus().equals(GameState.starting)){
                        if(it.hasNext()){
                            playAgain.setItem(config.getString("playagain-menu-items.arena-item.material"), Integer.parseInt(it.next()), arena.getStatus().equals(GameState.starting), Utils.format(config.getString("playagain-menu-items.arena-item.displayname"), arena), Utils.format(config.getStringList("playagain-menu-items.arena-item.lore"), arena), arena.getArenaName());
                        }
                    }
                }
            }
        }.runTaskTimer(SpectatorOptions.getInstance(), 20L, 20L);

    }
}
