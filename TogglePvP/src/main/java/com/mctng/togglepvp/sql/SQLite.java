package com.mctng.togglepvp.sql;

import com.mctng.togglepvp.TogglePvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class SQLite {

    private TogglePvP plugin;
    private String filePath;

    public SQLite(TogglePvP plugin, String filePath){
        this.plugin = plugin;
        this.filePath = filePath;
    }

    private Connection connect(){
        // SQLite connection string
        String url = "jdbc:sqlite:" + this.filePath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable(){
        String url = "jdbc:sqlite:" + this.filePath;

        // Create new table
        String sql = "CREATE TABLE IF NOT EXISTS pvp_list (\n"
                + " id integer PRIMARY KEY,\n"
                + " uuid text NOT NULL,\n"
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

    public void insertPlayer(Player player, int duration){
        String sql = "INSERT INTO pvp_list(uuid,duration) VALUES (?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getUniqueId().toString());
            pstmt.setDouble(2, duration);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(Player player){
        String sql = "DELETE FROM pvp_list WHERE uuid = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, player.getUniqueId().toString());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getPlayerDuration(Player player){
        String sql = "SELECT duration "
                + "FROM pvp_list WHERE uuid = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,player.getUniqueId().toString());
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            String s;
            if(rs.next()) {
                s = rs.getString(1);
                return Integer.parseInt(s);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

