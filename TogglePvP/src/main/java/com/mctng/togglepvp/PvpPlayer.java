package com.mctng.togglepvp;

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

}
