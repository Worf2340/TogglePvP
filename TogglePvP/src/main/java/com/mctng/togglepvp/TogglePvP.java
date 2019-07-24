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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class TogglePvP extends JavaPlugin {

    public static HashMap<UUID, PvpPlayer> pvpPlayers;
    public static SQLite SQLHandler;
    public static ArrayList<String> protectedPotions = new ArrayList<>();

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.getCommand("togglepvp").setExecutor(new com.mctng.togglepvp.commands.TogglePvP(this));
        this.getCommand("pvpstatus").setExecutor(new PvPStatus());
        this.getCommand("pvplist").setExecutor(new PvpList());
        this.getServer().getPluginManager().registerEvents(new OnPvp(this), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerLeave(), this);

        if (getConfig().getBoolean("protection.potions.types.splash-potions")){
            this.getServer().getPluginManager().registerEvents(new OnSplashPotion(this), this);
        }

        if (getConfig().getBoolean("protection.potions.types.lingering-potions")){
            this.getServer().getPluginManager().registerEvents(new OnLingeringPotionEffect(), this);
            this.getServer().getPluginManager().registerEvents(new OnLingeringPotionSplash(), this);
        }

        if (this.getConfig().getBoolean("protection.potions.effects.weakness")){
            protectedPotions.add("WEAKNESS");
        }
        if (this.getConfig().getBoolean("protection.potions.effects.poison")){
            protectedPotions.add("POISON");
        }
        if (this.getConfig().getBoolean("protection.potions.effects.slowness")){
            protectedPotions.add("SLOW");
        }


        pvpPlayers = new HashMap<>();

        // Create TogglePvP folder
        if (Files.notExists(Paths.get("plugins/TogglePvP"))){
            File dir = new File("plugins/TogglePvP");
            dir.mkdir();
        }

        // Initialize SQLite DB
        SQLHandler = new SQLite(this, "plugins/TogglePvP/pvp_list.db");
        SQLHandler.createNewTable("pvp_list");
        SQLHandler.deleteZeros();

        if (!(this.getConfig().getBoolean("protection.combat.melee"))){
            getLogger().warning("Not protecting against melee attacks.");
        }


        BukkitTask protectionExpirationTask = new ProtectionExpirationTask(this).runTaskTimer(this, 0, 1);
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
