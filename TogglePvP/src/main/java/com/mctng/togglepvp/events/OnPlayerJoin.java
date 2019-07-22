package com.mctng.togglepvp.events;

import com.mctng.togglepvp.PvpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.mctng.togglepvp.TogglePvP.*;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        // Update UUID cache
        if (uuidCacheDb.getUuidFromName(player.getName()) != null){
            if (!(uuidCacheDb.getUuidFromName(player.getName()).equals(player.getUniqueId().toString()))){
                uuidCacheDb.deleteUuidCache(player.getUniqueId().toString());
                uuidCacheDb.insertUuidCache(player.getUniqueId().toString(), player.getName());
            }
        }
        else {
            uuidCacheDb.insertUuidCache(player.getUniqueId().toString(), player.getName());
        }

        Integer duration = pvpListDb.getPlayerDuration(player);
        if (duration != null){
            PvpPlayer pvpPlayer = new PvpPlayer(player, true, duration);
            pvpPlayers.put(player.getUniqueId(), pvpPlayer);
            pvpListDb.deletePlayer(player);
            player.sendMessage(pvpPlayer.hasProtectionEnabled(false));
        }
    }
}
