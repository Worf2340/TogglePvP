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

    @SuppressWarnings("Duplicates")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if ((args.length != 2) && (args.length != 3)) {
            return false;
        }

        // Parse player from args
        Player player = Bukkit.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player name!");
            return true;
        }

        // Permission checks
        if (args[1].equalsIgnoreCase("on")) {
            if (player == sender){
                if (!(sender.hasPermission("togglepvp.togglepvp.on"))){
                    sender.sendMessage(ChatColor.RED + "You are missing the required permission togglepvp.togglepvp.on");
                    return true;
                }
            }
            else {
                if (!(sender.hasPermission("togglepvp.togglepvp.others.on"))){
                    sender.sendMessage(ChatColor.RED + "You are missing the required permission togglepvp.togglepvp.others.on");
                    return true;
                }
            }
        }
        else if (args[1].equalsIgnoreCase("off")){
            if (player == sender) {
                if (!(sender.hasPermission("togglepvp.togglepvp.off"))){
                    sender.sendMessage(ChatColor.RED + "You are missing the required permission togglepvp.togglepvp.off");
                    return true;
                }
            }
            else {
                if (!(sender.hasPermission("togglepvp.togglepvp.others.off"))){
                    sender.sendMessage(ChatColor.RED + "You are missing the required permission togglepvp.togglepvp.others.off");
                    return true;
                }
            }
        }



            // PvP protection untimed
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("on")) {

                // Check if player needs togglepvp.togglepvp.others.on permission
                if (player != sender){

                }

                // Do nothing if player is not PvP protected
                if (!(pvpPlayers.containsKey(player.getUniqueId()))) {
                    sender.sendMessage(ChatColor.RED + "PvP protection already disabled for " + player.getName() + ".");
                    return true;
                }
                if (!(pvpPlayers.get(player.getUniqueId()).hasProtection)) {
                    sender.sendMessage(ChatColor.RED + "PvP protection already disabled for " + player.getName() + ".");
                    return true;
                }

                pvpPlayers.get(player.getUniqueId()).hasProtection = false;
                pvpPlayers.get(player.getUniqueId()).duration = 0;
                sender.sendMessage(ChatColor.RED + "PvP protection disabled for " + player.getName() + ".");
                // Notify the player their protection has been disabled
                if (sender != player){
                    player.sendMessage(ChatColor.RED + "Your PvP protection has been disabled.");
                }
                return true;
            }
            else if (args[1].equalsIgnoreCase("off")) {
                // Create a new PvpPlayer object if one doesn't already exist in pvpPlayers
                PvpPlayer pvpPlayer;
                if (pvpPlayers.containsKey(player.getUniqueId())) {
                    if (pvpPlayers.get(player.getUniqueId()).hasProtection) {
                        sender.sendMessage(ChatColor.GREEN + "PvP protection already enabled for " + player.getName() + ".");
                        return true;
                    }
                    pvpPlayer = pvpPlayers.get(player.getUniqueId());
                    pvpPlayer.hasProtection = true;
                    pvpPlayer.duration = -1;
                } else {
                    pvpPlayer = new PvpPlayer(player, true, -1);
                }
                pvpPlayers.put(player.getUniqueId(), pvpPlayer);
                sender.sendMessage(ChatColor.GREEN + "PvP protection enabled for " + player.getName() + ".");
                if (sender != player){
                    player.sendMessage(ChatColor.GREEN + "You now have PvP protection enabled.");
                }
                return true;
            }
            else {
                return false;
            }
        }
        // PvP protection timed
        else {
            //region Parse time units
            String units = args[2].substring(args[2].length() - 1);
            int delay;

            try {
                delay = Integer.parseInt(args[2].substring(0, args[2].length() - 1));
            }
            catch (NumberFormatException e){
                player.sendMessage(ChatColor.RED + "Please enter a valid time!");
                return true;
            }

            int multiple;
            if (units.equalsIgnoreCase("m")) {
                multiple = 1200;
            } else if (units.equalsIgnoreCase("h")) {
                multiple = 72000;
            }
            else if (units.equalsIgnoreCase("s")){
                multiple = 20;
            }
            else {
                player.sendMessage(ChatColor.RED + "The valid time units are hours(h) and minutes(m).");
                return true;
            }

            int adjustedDelay = multiple * delay;
            //endregion
            if (args[1].equalsIgnoreCase("on")) {
                sender.sendMessage("You can only use the duration parameter to disable a player's pvp.");
                return true;
            }
            else if (args[1].equalsIgnoreCase("off")) {
                // Create a new PvpPlayer object if one doesn't already exist in pvpPlayers
                PvpPlayer pvpPlayer;
                if (pvpPlayers.containsKey(player.getUniqueId())) {
                    if (pvpPlayers.get(player.getUniqueId()).hasProtection) {
                        sender.sendMessage(ChatColor.GREEN + "PvP protection already enabled for " + player.getName() + ".");
                        return true;
                    }
                    pvpPlayer = pvpPlayers.get(player.getUniqueId());
                    pvpPlayer.hasProtection = true;
                    pvpPlayer.duration = adjustedDelay;
                } else {
                    pvpPlayer = new PvpPlayer(player, true, adjustedDelay);
                }
                pvpPlayers.put(player.getUniqueId(), pvpPlayer);
                sender.sendMessage(ChatColor.GREEN + "PvP protection enabled for " + player.getName() + " for " + PvpPlayer.ticksToTime(adjustedDelay) + ".");
                if (sender != player){
                    player.sendMessage(ChatColor.GREEN + "You now have PvP protection enabled for " + PvpPlayer.ticksToTime(adjustedDelay) + ".");
                }
                return true;
            }
            else {
                return false;
            }
        }
    }

}
