package com.mctng.togglepvp.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class onPvP implements Listener {

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) event.getEntity();

        // Cancel PvP if the attacker is a player who has PvP protection
        if ((event.getDamager() instanceof Player) && (pvpPlayers.containsKey(event.getDamager().getUniqueId())) && (event.getEntity() instanceof Player)) {
            event.setCancelled(true);
            event.getDamager().sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
            return;
        }

        // Cancel PvP if the attacker is a PvP protected player who threw a splash potion
        if ((event.getDamager() instanceof ThrownPotion) && (((ThrownPotion) event.getDamager()).getShooter() instanceof Player) && (event.getEntity() instanceof Player)) {
            Player shooter = (Player) ((ThrownPotion) event.getDamager()).getShooter();
            if ((pvpPlayers.containsKey(shooter.getUniqueId())) && (shooter != event.getEntity())) {
                event.setCancelled(true);
                shooter.sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                return;
            }
        }


        //region Lingering Potion Code
        //        // Cancel PvP if the attacker is a PvP protected player who threw a lingering potion
//        if ((event.getDamager() instanceof LingeringPotion) && (((LingeringPotion) event.getDamager()).getShooter() instanceof Player) && (event.getEntity() instanceof Player)){
//            Player shooter = (Player) ((LingeringPotion) event.getDamager()).getShooter();
//            if ((pvpList.contains(shooter.getUniqueId())) && (shooter != event.getEntity())){
//                event.setCancelled(true);
//                shooter.sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
//                return;
//            }
//        }

//        if (event.getDamager() instanceof LingeringPotion) {
//            System.out.println(event.getDamager());
//        }
        //endregion

        // Cancel PvP if the defender has PvP protection
        if ((event.getEntity() instanceof Player) && (pvpPlayers.containsKey(event.getEntity().getUniqueId()))) {
            if (event.getDamager() instanceof Player) {
                event.setCancelled(true);
                event.getDamager().sendMessage(ChatColor.RED + "That player has PvP protection!");
                return;
            }
            if (event.getDamager() instanceof ThrownPotion) {
                ThrownPotion potion = (ThrownPotion) event.getDamager();
                if (potion.getShooter() instanceof Player) {
                    if (potion.getShooter() != p) {
                        event.setCancelled(true);
                        ((Player) potion.getShooter()).sendMessage(ChatColor.RED + "That player has PvP protection!");
                    }
                }
            }
        }

    }
}
