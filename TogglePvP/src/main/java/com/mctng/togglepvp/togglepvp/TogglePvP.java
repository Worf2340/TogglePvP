package com.mctng.togglepvp.togglepvp;

import com.mctng.togglepvp.togglepvp.commands.PvPStatus;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class TogglePvP extends JavaPlugin {

    public static ArrayList<UUID> pvpList;

    @Override
    public void onEnable() {
        this.getCommand("togglepvp").setExecutor(new TogglePvP());
        this.getCommand("pvpstatus").setExecutor(new PvPStatus());
        this.getServer().getPluginManager().registerEvents(new MyEvents(), this);

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
