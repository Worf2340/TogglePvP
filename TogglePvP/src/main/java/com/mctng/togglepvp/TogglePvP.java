package com.mctng.togglepvp;

import com.mctng.togglepvp.commands.PvPStatus;
import com.mctng.togglepvp.events.*;
import com.mctng.togglepvp.sql.PvpList;
import com.mctng.togglepvp.sql.UuidCache;
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
    public static PvpList pvpListDb;
    public static UuidCache uuidCacheDb;

    @Override
    public void onEnable() {
        this.getCommand("togglepvp").setExecutor(new com.mctng.togglepvp.commands.TogglePvP(this));
        this.getCommand("pvpstatus").setExecutor(new PvPStatus());
        this.getCommand("pvplist").setExecutor(new com.mctng.togglepvp.commands.PvpList());
        this.getServer().getPluginManager().registerEvents(new OnPvp(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);
        //this.getServer().getPluginManager().registerEvents(new OnPotionEffect(), this);
        this.getServer().getPluginManager().registerEvents(new OnSplashPotion(), this);



        pvpPlayers = new HashMap<>();

        // Create TogglePvP folder
        if (Files.notExists(Paths.get("plugins/TogglePvP"))){
            File dir = new File("plugins/TogglePvP");
            dir.mkdir();
        }

        pvpListDb = new PvpList(this, "plugins/TogglePvP/pvp_list.db");
        uuidCacheDb = new UuidCache("plugins/TogglePvP/uuid_cache.db");

        pvpListDb.createNewPvPListTable("pvp_list");
        uuidCacheDb.createNewUuidCacheTable();
        pvpListDb.deleteZeros();

        BukkitTask protectionExpirationTask = new ProtectionExpirationTask(this).runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        pvpPlayers.forEach((UUID, pvpPlayer) -> {
            if (pvpPlayer.duration != 0) {
                System.out.println("Saving data for2 " + pvpPlayer.player.getName());
                pvpListDb.insertPlayer(pvpPlayer.player, pvpPlayer.duration);
            }
        });
    }
}
