package com.mctng.togglepvp.sql;

import java.util.logging.Level;
import com.mctng.togglepvp.TogglePvP;

public class Error {
    public static void execute(TogglePvP plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(TogglePvP plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}

