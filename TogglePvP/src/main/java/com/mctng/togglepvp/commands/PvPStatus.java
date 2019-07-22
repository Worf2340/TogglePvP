package com.mctng.togglepvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class PvPStatus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0 && args.length != 1){
            return false;
        }

        // If the player is checking their own Pvp status
        if ((args.length == 0) || (args[0].equalsIgnoreCase(sender.getName()))) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must specify a player argument to use this command from the console.");
                return true;
            }

            Player player = (Player) sender;
            System.out.println(player.getUniqueId());

            if (pvpPlayers.containsKey(player.getUniqueId())) {
                player.sendMessage(pvpPlayers.get(player.getUniqueId()).hasProtectionEnabled(false));
            } else {
                player.sendMessage(ChatColor.RED + "You have PvP protection disabled.");
            }

        }
        // If the player is checking another player's pvp status
        else {

            if (!(sender.hasPermission("togglepvp.pvpstatus.others"))){
                sender.sendMessage(ChatColor.RED + "You are missing the required permission togglepvp.pvpstatus.others.");
                return true;
            }

            Player checkPlayer = Bukkit.getServer().getPlayer(args[0]);

            if (checkPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Invalid player name!");
                return true;
            }

            Player player = (Player) sender;

            if (player.hasPermission("togglepvp.pvpstatus.others")) {
                if (pvpPlayers.containsKey(checkPlayer.getUniqueId())) {
                    player.sendMessage(pvpPlayers.get(checkPlayer.getUniqueId()).hasProtectionEnabled(true));
                } else {
                    player.sendMessage(ChatColor.RED + "PvP protection is disabled for " + checkPlayer.getName() + ".");
                }
            }
        }

        return true;
    }
}
