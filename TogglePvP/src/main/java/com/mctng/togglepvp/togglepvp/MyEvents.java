package com.mctng.togglepvp.togglepvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class MyEvents implements Listener {
//    @EventHandler
//    public void onMove(PlayerMoveEvent event){
//        Player player = event.getPlayer();
//        player.sendMessage(ChatColor.GREEN + "You're movin.");
//    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event){
        System.out.println(TogglePvP.pvpList);
        Player p = (Player) event.getEntity();
        if (event.getDamager() instanceof Player){
            if(TogglePvP.pvpList.contains(p.getUniqueId())){
                event.setCancelled(true);
                event.getDamager().sendMessage(ChatColor.RED + "That player has PvP disabled!");
            }

        }
    }
}
