package com.mctng.togglepvp.sql;

import java.sql.*;

public class UuidCache {

    private String filePath;

    public UuidCache(String filePath){
        this.filePath = filePath;
    }

    private Connection connect() {
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

    public void createNewUuidCacheTable(){
        String url = "jdbc:sqlite:" + this.filePath;

        // Create new table
        String sql = "CREATE TABLE IF NOT EXISTS uuid_cache (\n"
                + " id integer PRIMARY KEY,\n"
                + " uuid text NOT NULL,\n"
                + " username text NOT NULL\n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUuidCache(String uuid, String username){
        String sql = "INSERT INTO uuid_cache(uuid,username) VALUES (?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUuidFromName(String username){
        String sql = "SELECT uuid "
                + "FROM uuid_cache WHERE username = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,username);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            String s;
            if(rs.next()) {
                s = rs.getString(1);
                return s;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteUuidCache(String uuid){
        String sql = "DELETE FROM uuid_cache WHERE uuid = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, uuid);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
