package com.mctng.togglepvp;

import com.mctng.togglepvp.commands.PvPStatus;
import com.mctng.togglepvp.events.OnPlayerJoin;
import com.mctng.togglepvp.events.OnPlayerLeave;
import com.mctng.togglepvp.events.OnPvp;
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
        this.getServer().getPluginManager().registerEvents(new OnPvp(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);


        pvpPlayers = new HashMap<>();

        // Create TogglePvP folder
        if (Files.notExists(Paths.get("plugins/TogglePvP"))){
            File dir = new File("plugins/TogglePvP");
            dir.mkdir();
        }

        SQLHandler = new SQLite(this, "plugins/TogglePvP/pvp_list.db");
        SQLHandler.createNewTable();

        BukkitTask protectionExpirationTask = new ProtectionExpirationTask(this).runTaskTimer(this, 0, 1);
        this.getLogger().info("Running latest branch.");
    }

    @Override
    public void onDisable() {
        pvpPlayers.forEach((UUID, pvpPlayer) -> {
            SQLHandler.insertPlayer(pvpPlayer.player, pvpPlayer.duration);
        });
    }
}
