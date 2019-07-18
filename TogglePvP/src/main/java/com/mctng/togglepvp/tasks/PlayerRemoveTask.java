package com.mctng.togglepvp.tasks;

import com.mctng.togglepvp.TogglePvP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerRemoveTask extends BukkitRunnable {

    private Player player;
    private final TogglePvP plugin;

    public PlayerRemoveTask(TogglePvP plugin, Player player){
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {

        TogglePvP.pvpList.remove(this.player.getUniqueId());
        this.player.sendMessage(ChatColor.RED + "PvP protection disabled.");
        System.out.println("h?");
    }
}
