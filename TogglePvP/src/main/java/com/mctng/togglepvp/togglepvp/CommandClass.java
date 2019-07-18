package com.mctng.togglepvp.togglepvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.ArrayList;

import static com.mctng.togglepvp.togglepvp.TogglePvP.pvpList;

public class CommandClass implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("healme")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (args.length < 1){
                    player.sendMessage(ChatColor.RED + "Usage: /healme [amount]");
                    return true;
                } else if (args.length == 1) {
                    try {
                        double phealth = player.getHealth();
                        double addHealth = Double.parseDouble(args[0]);
                        if (phealth < 20){
                            player.setHealth(phealth + addHealth);
                            player.sendMessage(ChatColor.GREEN + "You have been healed for " + addHealth + " health.");
                            return true;
                        } else {
                            player.sendMessage(ChatColor.YELLOW + "Invalid health number.");
                            return true;
                        }
                    } catch (NumberFormatException e){
                        player.sendMessage(ChatColor.RED + "Please input a number.");
                        return true;
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            }
        }
        if (command.getName().equalsIgnoreCase("togglepvp")){
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


        }
        return true;
    }
}
