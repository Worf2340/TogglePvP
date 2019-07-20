package com.mctng.togglepvp.commands;

import com.mctng.togglepvp.PvpPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class TogglePvP implements CommandExecutor {

    private final com.mctng.togglepvp.TogglePvP plugin;

    public TogglePvP(com.mctng.togglepvp.TogglePvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if ((args.length != 2) && (args.length != 3)){
            return false;
        }

        // Parse player from args
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player == null){
            sender.sendMessage(ChatColor.RED + "Invalid player name!");
            return true;
        }


        //region Pvp Protection Untimed
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("on")) {
                // Do nothing if player is not PvP protected
                if (!(pvpPlayers.containsKey(player.getUniqueId()))){
                    sender.sendMessage(ChatColor.RED + "PvP protection already disabled for " + player.getName() + ".");
                    return true;
                }
                pvpPlayers.get(player.getUniqueId()).hasProtection = false;
                pvpPlayers.get(player.getUniqueId()).duration = 0;
                sender.sendMessage(ChatColor.RED + "PvP protection disabled for " + player.getName() + ".");
                return true;

            }
            else if (args[1].equalsIgnoreCase("off")) {
                // Create a new PvpPlayer object if one doesn't already exist in pvpPlayers
                PvpPlayer pvpPlayer;
                if (pvpPlayers.containsKey(player.getUniqueId())){
                    pvpPlayer = pvpPlayers.get(player.getUniqueId());
                    pvpPlayer.hasProtection = true;
                    pvpPlayer.duration = -1;
                }
                else {
                    pvpPlayer = new PvpPlayer(player, true, -1);
                }
                pvpPlayers.put(player.getUniqueId(), pvpPlayer);
                sender.sendMessage(ChatColor.GREEN + "PvP protection enabled for " + player.getName() + ".");
                return true;
            }
            else {
                return false;
            }
        }
        //endregion

        //<editor-fold desc="PvP Protection Timed">
//        String units = args[2].substring(args[2].length()-1);
//        int delay = Integer.parseInt(args[2].substring(0, args[2].length()-1));
//        int multiple;
//
//
//        if (units.equalsIgnoreCase("s")){
//            multiple = 20;
//        }
//        else if (units.equalsIgnoreCase("m")){
//            multiple = 1200;
//        }
//        else if (units.equalsIgnoreCase("h")){
//            multiple = 72000;
//        }
//        else {
//            return false;
//        }
//
//        System.out.println(units);
//        System.out.println(delay);
//        int adjustedDelay = multiple * delay;
//
//
//        if (args[1].equalsIgnoreCase("on")) {
//            sender.sendMessage(ChatColor.GREEN + "PvP protection disabled for " + player.getName() + ".");
//            pvpList.remove(player.getUniqueId());
//            return true;
//        }
//        else if (args[1].equalsIgnoreCase("off")) {
//            sender.sendMessage(ChatColor.GREEN + "PvP protection enabled for " + player.getName() + ".");
//            pvpList.add(player.getUniqueId());
//            new PlayerRemoveTask(plugin, player).runTaskLater(plugin, adjustedDelay);
//            return true;
//        }
//        else {
//            //sender.sendMessage(ChatColor.RED + "Usage: /togglepvp [player] [on/off]");
//            return false;
//        }
        //</editor-fold>
        return true;
    }

}
