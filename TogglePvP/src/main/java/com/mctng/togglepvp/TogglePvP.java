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
import java.util.UUID;

public final class TogglePvP extends JavaPlugin {

    public static ArrayList<UUID> pvpList;
//    private Database db;

    @Override
    public void onEnable() {
        this.getCommand("togglepvp").setExecutor(new com.mctng.togglepvp.commands.TogglePvP(this));
        this.getCommand("pvpstatus").setExecutor(new PvPStatus());
        this.getServer().getPluginManager().registerEvents(new onPvP(), this);

        // Create TogglePvP folder
        if (Files.notExists(Paths.get("plugins/TogglePvP"))){
            File dir = new File("plugins/TogglePvP");
            dir.mkdir();
        }


        SQLite SQLHandler = new SQLite(this, "plugins/TogglePvP/pvp_list.db");
        SQLHandler.connect();
        SQLHandler.createNewTable();

        pvpList = new ArrayList<>();


        // Load pvplist data
        try
        {
            FileInputStream fis = new FileInputStream("plugins/pvp_list");
            ObjectInputStream ois = new ObjectInputStream(fis);

            pvpList = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (FileNotFoundException e){
            getLogger().info("No pvp_list file found. Creating one on server shutdown.");
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Save pvplist to file
        try
        {
            FileOutputStream fos = new FileOutputStream("plugins/pvp_list");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pvpList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
}
