package com.mctng.togglepvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mctng.togglepvp.TogglePvP.pvpList;

public class PvPStatus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0 && args.length != 1){
            return false;
        }

        if (args.length == 0){
            if (!(sender instanceof Player)){
                sender.sendMessage("You must specify a player argument to use this command from the console.");
                return true;
            }

            Player player = (Player) sender;

            if (pvpList.contains(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You have PvP disabled.");
            }
            else {
                player.sendMessage(ChatColor.GREEN + "You have PvP enabled.");
            }
            return true;

        }

        Player checkPlayer = Bukkit.getServer().getPlayer(args[0]);

        if (checkPlayer == null){
            sender.sendMessage(ChatColor.RED + "Invalid player name!");
            return true;
        }

        Player player = (Player) sender;


        if (player.hasPermission("togglepvp.pvpstatus.others")) {
            if (pvpList.contains(checkPlayer.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "PvP is disabled for " + checkPlayer.getName());
            }
            else {
                player.sendMessage(ChatColor.GREEN + "PvP is enabled for " + checkPlayer.getName());
            }
            return true;
        }
        else {
            player.sendMessage(ChatColor.RED + "You don't have the required permission node togglepvp.pvpstatus.others.");
        }

        return true;
    }
}
