package com.mctng.togglepvp.tasks;

import com.mctng.togglepvp.TogglePvP;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class ProtectionExpirationTask extends BukkitRunnable {
    private final TogglePvP plugin;

    public ProtectionExpirationTask(TogglePvP plugin){
        this.plugin = plugin;
    }

    @Override
    public void run(){
        pvpPlayers.forEach((UUID, pvpPlayer) -> {
          if (pvpPlayer.hasProtection){
              if (pvpPlayer.duration != -1){
                  pvpPlayer.duration -= 1;
                  if (pvpPlayer.duration == 0){
                      pvpPlayer.hasProtection = false;
                      pvpPlayer.player.sendMessage(ChatColor.RED + "Your PvP protection is now disabled.");
                  }
                  else if (pvpPlayer.duration == 36000){
                      pvpPlayer.player.sendMessage(ChatColor.YELLOW + "Warning: Your PvP protection will expire in 30 minutes.");
                  }
                  else if (pvpPlayer.duration == 18000) {
                      pvpPlayer.player.sendMessage(ChatColor.YELLOW + "Warning: Your PvP protection will expire in 15 minutes.");
                  }
                  else if (pvpPlayer.duration == 6000) {
                      pvpPlayer.player.sendMessage(ChatColor.YELLOW + "Warning: Your PvP protection will expire in 5 minutes.");
                  }
                  else if (pvpPlayer.duration == 1200) {
                      pvpPlayer.player.sendMessage(ChatColor.YELLOW + "Warning: Your PvP protection will expire in 1 minute.");
                  }
              }
          }
        });
    }

}
