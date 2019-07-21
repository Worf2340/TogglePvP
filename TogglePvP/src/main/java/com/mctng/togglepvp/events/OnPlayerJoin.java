package com.mctng.togglepvp.events;

import com.mctng.togglepvp.PvpPlayer;
import com.mctng.togglepvp.sql.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.mctng.togglepvp.TogglePvP.SQLHandler;
import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Integer duration = SQLHandler.getPlayerDuration(player);
        if (duration != null){
            PvpPlayer pvpPlayer = new PvpPlayer(player, true, duration);
            pvpPlayers.put(player.getUniqueId(), pvpPlayer);
            SQLHandler.deletePlayer(player);
            player.sendMessage(pvpPlayer.hasProtectionEnabled(false));
        }
    }
}
