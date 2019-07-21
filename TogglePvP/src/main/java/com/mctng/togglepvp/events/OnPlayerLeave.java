package com.mctng.togglepvp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.mctng.togglepvp.TogglePvP.SQLHandler;
import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnPlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event){
        Player leftPlayer = event.getPlayer();
        if (pvpPlayers.containsKey(leftPlayer.getUniqueId())){
            if (pvpPlayers.get(leftPlayer.getUniqueId()).hasProtection) {
                // Insert player data into database
                SQLHandler.insertPlayer(event.getPlayer(), pvpPlayers.get(leftPlayer.getUniqueId()).duration);
                System.out.println("Saving data for " + event.getPlayer().getName());
                pvpPlayers.remove(leftPlayer.getUniqueId());
            }
        }
    }
}
