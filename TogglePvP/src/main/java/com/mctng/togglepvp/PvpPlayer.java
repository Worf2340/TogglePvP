package com.mctng.togglepvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;

public class PvpPlayer {
    public Player player;
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
                if (this.duration == -1) {
                    protectionEnabled = ChatColor.GREEN + "You have PvP protection enabled.";
                }
                else {
                    protectionEnabled = ChatColor.GREEN + "You have PvP protection enabled for " + this.ticksToTime(this.duration) + ".";
                }
            }
            else {
                if (this.duration == -1){
                    protectionEnabled = ChatColor.GREEN + "PvP protection is enabled for " + this.player.getName();
                }
                else {
                    protectionEnabled = ChatColor.GREEN + "PvP protection is enabled for " + this.player.getName();
                }
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

    private String ticksToTime(int ticks){
        int seconds = ticks / 20;
        Duration duration = Duration.ofSeconds(seconds);
        return formatDuration(duration);
    }

    private static String formatDuration(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    public void printPlayer(){
        String player = "Name: " + this.player.getName() + ", Duration: " + this.duration;
        System.out.println(player);
    }

}
