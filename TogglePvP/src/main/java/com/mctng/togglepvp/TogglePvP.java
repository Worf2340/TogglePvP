package com.mctng.togglepvp;

import com.mctng.togglepvp.commands.PvPStatus;
import com.mctng.togglepvp.commands.PvpList;
import com.mctng.togglepvp.events.*;
import com.mctng.togglepvp.sql.SQLite;
import com.mctng.togglepvp.tasks.ProtectionExpirationTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

public final class TogglePvP extends JavaPlugin {

    public static HashMap<UUID, PvpPlayer> pvpPlayers;
    public static SQLite SQLHandler;

    @Override
    public void onEnable() {
        this.getCommand("togglepvp").setExecutor(new com.mctng.togglepvp.commands.TogglePvP(this));
        this.getCommand("pvpstatus").setExecutor(new PvPStatus());
        this.getCommand("pvplist").setExecutor(new PvpList());
        this.getServer().getPluginManager().registerEvents(new OnPvp(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);
        //this.getServer().getPluginManager().registerEvents(new OnPotionEffect(), this);
        this.getServer().getPluginManager().registerEvents(new OnSplashPotion(), this);
        this.getServer().getPluginManager().registerEvents(new OnLingeringPotionEffect(), this);
        this.getServer().getPluginManager().registerEvents(new OnLingeringPotionSplash(), this);




        pvpPlayers = new HashMap<>();

        // Create TogglePvP folder
        if (Files.notExists(Paths.get("plugins/TogglePvP"))){
            File dir = new File("plugins/TogglePvP");
            dir.mkdir();
        }

        SQLHandler = new SQLite(this, "plugins/TogglePvP/pvp_list.db");
        SQLHandler.createNewTable("pvp_list");
        SQLHandler.deleteZeros();

        BukkitTask protectionExpirationTask = new ProtectionExpirationTask(this).runTaskTimer(this, 0, 1);
        getLogger().info("Running add-pvplist branch");
    }

    @Override
    public void onDisable() {
        pvpPlayers.forEach((UUID, pvpPlayer) -> {
            if (pvpPlayer.duration != 0) {
                System.out.println("Saving data for2 " + pvpPlayer.player.getName());
                SQLHandler.insertPlayer(pvpPlayer.player, pvpPlayer.duration);
            }
        });
    }
}
