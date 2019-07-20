package com.mctng.togglepvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class PvpPlayer {
    private Player player;
    public boolean hasProtection;
    public int duration;
    private LocalDateTime timeProtectionPlaced;

    public PvpPlayer(Player player, boolean hasProtection, int duration){
        this.player = player;
        this.hasProtection = hasProtection;
        this.duration = duration;
        this.timeProtectionPlaced = LocalDateTime.now();
    }

    // Returns formatted string on if protection is enabled
    public String hasProtectionEnabled(boolean thirdPerson){
        String protectionEnabled;
        if (this.hasProtection){
            if (!(thirdPerson)) {
                    protectionEnabled = ChatColor.GREEN + "You have PvP protection enabled.";
            }
            else {
                protectionEnabled = ChatColor.GREEN + "PvP protection is enabled for " + this.player.getName();
            }
        }
        else {
            if (!(thirdPerson)) {
                protectionEnabled = ChatColor.RED + "You have PvP protection disabled.";
            }
            else {
                protectionEnabled = ChatColor.RED + "PvP protection is disabled for " + this.player.getName() + ".";
            }
        }
        return protectionEnabled;
    }

}
