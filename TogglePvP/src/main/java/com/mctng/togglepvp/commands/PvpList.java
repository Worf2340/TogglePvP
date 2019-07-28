package com.mctng.togglepvp.commands;

import com.mctng.togglepvp.PvpPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.mctng.togglepvp.TogglePvP.SQLHandler;
import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class PvpList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((args.length != 0) && (args.length != 1)){
            return false;
        }

        if (args.length == 0) {
            if (!(sender.hasPermission("togglepvp.pvplist"))) {
                return false;
            }

            int count = 0;
            if (pvpPlayers.size() == 0) {
                sender.sendMessage(ChatColor.RED + "There are no PvP protected players.");
                return true;
            }
            String bold = ChatColor.BOLD + "";


            ArrayList<PvpPlayer> protectedPlayers = new ArrayList<PvpPlayer>();

            // Get protected players
            for (Map.Entry<UUID, PvpPlayer> entry : pvpPlayers.entrySet()) {
                if (entry.getValue().hasProtection) {
                    protectedPlayers.add(entry.getValue());
                }
            }

            // Print protected players
            if (protectedPlayers.size() == 0) {
                sender.sendMessage(ChatColor.RED + "There are no online PvP protected players.");
            } else {
                sender.sendMessage(ChatColor.GRAY + bold + "Online PvP protected players:");

                for (PvpPlayer player : protectedPlayers) {
                    if (player.duration == -1) {
                        sender.sendMessage(ChatColor.GREEN + player.player.getName());
                    } else {
                        sender.sendMessage((ChatColor.YELLOW + player.player.getName() + " (" + player.ticksToTime()) + ")");
                    }
                }
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("offline")){
            HashMap<String, Integer> protectedPlayers = SQLHandler.getAllPlayers();
            if ((protectedPlayers != null)) {
                if (protectedPlayers.size() != 0){
                    String bold = ChatColor.BOLD + "";
                    sender.sendMessage(ChatColor.GRAY + bold + "Offline PvP protected players:");
                    for (Map.Entry<String, Integer> entry : protectedPlayers.entrySet()) {
                        if (entry.getValue() == -1) {
                            sender.sendMessage(ChatColor.GREEN + entry.getKey());
                        }
                        else {
                            sender.sendMessage(ChatColor.YELLOW + entry.getKey() + " (" + PvpPlayer.ticksToTime(entry.getValue()) + ")");
                        }
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "There are no offline PvP protected players.");
                }
            }

        }
        return true;
    }
}
