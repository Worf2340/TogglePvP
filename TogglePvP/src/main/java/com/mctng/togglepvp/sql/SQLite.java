package com.mctng.togglepvp.sql;

import com.mctng.togglepvp.TogglePvP;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.bukkit.Bukkit.getLogger;

public class SQLite {

    private TogglePvP plugin;
    private String filePath;

    public SQLite(TogglePvP plugin, String filePath){
        this.plugin = plugin;
        this.filePath = filePath;
    }

    public void connect(){
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:" + this.filePath;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            this.plugin.getLogger().info("Connection to SQLite Database has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void createNewTable(){
        String url = "jdbc:sqlite:" + this.filePath;

        // Create new table
        String sql = "CREATE TABLE IF NOT EXISTS pvp_list (\n"
                + " id integer PRIMARY KEY,\n"
                + " player text NOT NULL,\n"
                + " protection integer NOT NULL,\n"
                + " duration integer\n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement()){
            statement.execute(sql);
            this.plugin.getLogger().info("Initialized SQLite table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

