package com.mctng.togglepvp.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

@SuppressWarnings("Duplicates")
public class OnPvp implements Listener {

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) event.getEntity();

        // Melee PvP protection
        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getEntity();
            if (pvpPlayers.containsKey(player.getUniqueId())){
                if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && event.getDamager() != player) {
                    event.setCancelled(true);
                    event.getDamager().sendMessage(ChatColor.RED + "That player has PvP protection!");
                    return;
                }
            }
            else if (pvpPlayers.containsKey(event.getDamager().getUniqueId())){
                if ((pvpPlayers.get(event.getDamager().getUniqueId()).hasProtection) && event.getDamager() != player){
                    event.setCancelled(true);
                    event.getDamager().sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                    return;
                }
            }
        }

        // Lingering potion protection
        if (event.getDamager() instanceof AreaEffectCloud){
            AreaEffectCloud effectCloud = (AreaEffectCloud) event.getDamager();
            if (effectCloud.getSource() instanceof Player){
                Player player = (Player) event.getEntity();
                if (pvpPlayers.containsKey(player.getUniqueId())){
                    if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && effectCloud.getSource() != player) {
                        event.setCancelled(true);
                        ((Player) effectCloud.getSource()).sendMessage(ChatColor.RED + "That player has PvP protection!");
                        return;
                    }
                }
                else if (pvpPlayers.containsKey(((Player) effectCloud.getSource()).getUniqueId())){
                    if ((pvpPlayers.get(((Player) effectCloud.getSource()).getUniqueId()).hasProtection) && effectCloud.getSource() != player){
                        event.setCancelled(true);
                        ((Player) effectCloud.getSource()).sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                        return;
                    }
                }
            }
        }

        // Bow protection
        if (event.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player){
                Player player = (Player) event.getEntity();
                if (pvpPlayers.containsKey(player.getUniqueId())){
                    if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && arrow.getShooter() != player) {
                        event.setCancelled(true);
                        ((Player) arrow.getShooter()).sendMessage(ChatColor.RED + "That player has PvP protection!");
                        return;
                    }
                }
                else if (pvpPlayers.containsKey(((Player) arrow.getShooter()).getUniqueId())){
                    if ((pvpPlayers.get(((Player) arrow.getShooter()).getUniqueId()).hasProtection) && arrow.getShooter() != player){
                        event.setCancelled(true);
                        ((Player) arrow.getShooter()).sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                        return;
                    }
                }
            }
        }

        // Splash potion protection
        if (event.getDamager() instanceof ThrownPotion){
            ThrownPotion potion = (ThrownPotion) event.getDamager();
            if (potion.getShooter() instanceof Player){
                Player player = (Player) event.getEntity();
                if (pvpPlayers.containsKey(player.getUniqueId())){
                    if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && potion.getShooter() != player) {
                        event.setCancelled(true);
                        ((Player) potion.getShooter()).sendMessage(ChatColor.RED + "That player has PvP protection!");
                        return;
                    }
                }
                else if (pvpPlayers.containsKey(((Player) potion.getShooter()).getUniqueId())){
                    if ((pvpPlayers.get(((Player) potion.getShooter()).getUniqueId()).hasProtection) && potion.getShooter() != player){
                        event.setCancelled(true);
                        ((Player) potion.getShooter()).sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                        return;
                    }
                }
            }
        }


    }
}
