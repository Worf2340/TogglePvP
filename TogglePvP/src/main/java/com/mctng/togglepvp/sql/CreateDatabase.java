package com.mctng.togglepvp.sql;

import com.sun.corba.se.impl.orb.DataCollectorBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.JDBC;

public class CreateDatabase {
    public static boolean createNewDatabse(String fileName){
        String url = "jbdc:sqlite:C:\\Users\\vedan\\Documents\\Spigot 1.13 Server\\plugins\\TogglePvP" + fileName;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(url);
            if (con != null){
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("ERROR:");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
