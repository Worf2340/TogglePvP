package com.mctng.togglepvp.togglepvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.mctng.togglepvp.togglepvp.TogglePvP.pvpList;

public class TogglePvP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2){
            //sender.sendMessage(ChatColor.RED + "Usage: /togglepvp [player] [on/off]");
            return false;
        }

        Player player = Bukkit.getServer().getPlayer(args[0]);

        if (args[1].equalsIgnoreCase("on")){
            sender.sendMessage(ChatColor.RED + "PvP toggled on for " + player.getName());
            pvpList.remove(player.getUniqueId());
        }
        else if (args[1].equalsIgnoreCase("off")){
            sender.sendMessage(ChatColor.RED + "PvP toggled off for " + player.getName());
            pvpList.add(player.getUniqueId());
        }
        else {
            //sender.sendMessage(ChatColor.RED + "Usage: /togglepvp [player] [on/off]");
            return false;
        }

        return true;
    }

}
