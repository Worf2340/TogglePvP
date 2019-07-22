package com.mctng.togglepvp.sql;

import com.mctng.togglepvp.TogglePvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class PvpList {

    private TogglePvP plugin;
    private String filePath;

    public PvpList(TogglePvP plugin, String filePath){
        this.plugin = plugin;
        this.filePath = filePath;
    }

    private Connection connect(){
        // PvpList connection string
        String url = "jdbc:sqlite:" + this.filePath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewPvPListTable(String tableName){
        String url = "jdbc:sqlite:" + this.filePath;

        // Create new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n"
                + " id integer PRIMARY KEY,\n"
                + " uuid text NOT NULL,\n"
                + " duration integer\n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement()){
            statement.execute(sql);
            this.plugin.getLogger().info("Connected to PvpList database.");
            this.plugin.getLogger().info("Initialized PvpList table pvp_list.");
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

    public void deleteZeros(){
        String sql = "DELETE FROM pvp_list WHERE duration = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, 0);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

