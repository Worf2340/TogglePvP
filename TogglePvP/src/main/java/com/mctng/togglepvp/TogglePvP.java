package com.mctng.togglepvp;

import com.mctng.togglepvp.commands.PvPStatus;
import com.mctng.togglepvp.events.onPvP;
import com.mctng.togglepvp.sql.CreateDatabase;
import com.mctng.togglepvp.sql.SQLite;
import com.mctng.togglepvp.testsql.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class TogglePvP extends JavaPlugin {

    public static HashMap<UUID, PvpPlayer> pvpPlayers;

    @Override
    public void onEnable() {
        this.getCommand("togglepvp").setExecutor(new com.mctng.togglepvp.commands.TogglePvP(this));
        this.getCommand("pvpstatus").setExecutor(new PvPStatus());
        this.getServer().getPluginManager().registerEvents(new onPvP(), this);

        pvpPlayers = new HashMap<>();

        // Create TogglePvP folder
        if (Files.notExists(Paths.get("plugins/TogglePvP"))){
            File dir = new File("plugins/TogglePvP");
            dir.mkdir();
        }

        SQLite SQLHandler = new SQLite(this, "plugins/TogglePvP/pvp_list.db");
        SQLHandler.connect();
        SQLHandler.createNewTable();

    }

    @Override
    public void onDisable() {

    }
}
