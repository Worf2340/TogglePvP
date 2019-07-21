package com.mctng.togglepvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class PvpList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0){
            return false;
        }
        if (pvpPlayers.size() == 0){
            sender.sendMessage(ChatColor.RED + "There are no PvP protected players.");
            return true;
        }
        String bold = ChatColor.BOLD + "";

        sender.sendMessage(ChatColor.GRAY + bold +  "Current PvP protected players:");
        pvpPlayers.forEach((UUID, pvpPlayer) -> {
            if (pvpPlayer.hasProtection) {
                if (pvpPlayer.duration == -1) {
                    sender.sendMessage(ChatColor.GREEN + pvpPlayer.player.getName());
                }
                else {
                    sender.sendMessage((ChatColor.YELLOW + pvpPlayer.player.getName() + " (" + pvpPlayer.ticksToTime()) + ")");
                }
            }
        });

        return true;
    }
}
