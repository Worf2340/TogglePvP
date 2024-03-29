package com.mctng.togglepvp.events;

import com.mctng.togglepvp.TogglePvP;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

@SuppressWarnings("Duplicates")
public class OnPvp implements Listener {

    private TogglePvP plugin;

    public OnPvp(TogglePvP plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        // Melee PvP protection
        if (event.getDamager() instanceof Player){
            if (!(this.plugin.getConfig().getBoolean("protection.combat.melee"))){
                System.out.println("config");
                return;
            }
            Player player = (Player) event.getEntity();
            if (pvpPlayers.containsKey(event.getDamager().getUniqueId())){
                if ((pvpPlayers.get(event.getDamager().getUniqueId()).hasProtection) && event.getDamager() != player){
                    event.setCancelled(true);
                    event.getDamager().sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                    return;
                }
            }
            if (pvpPlayers.containsKey(player.getUniqueId())){
                if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && event.getDamager() != player) {
                    event.setCancelled(true);
                    event.getDamager().sendMessage(ChatColor.RED + "That player has PvP protection!");
                    return;
                }
            }
        }

        // Lingering potion protection
        if (event.getDamager() instanceof AreaEffectCloud){

            if (!(this.plugin.getConfig().getBoolean("protection.potions.types.lingering-potions"))){
                return;
            }
            if (!(this.plugin.getConfig().getBoolean("protection.potions.effects.instant-damage"))){
                return;
            }

            AreaEffectCloud effectCloud = (AreaEffectCloud) event.getDamager();
            if (effectCloud.getSource() instanceof Player){
                Player player = (Player) event.getEntity();
                if (pvpPlayers.containsKey(((Player) effectCloud.getSource()).getUniqueId())){
                    if ((pvpPlayers.get(((Player) effectCloud.getSource()).getUniqueId()).hasProtection) && effectCloud.getSource() != player){
                        event.setCancelled(true);
                        ((Player) effectCloud.getSource()).sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                        return;
                    }
                }
                if (pvpPlayers.containsKey(player.getUniqueId())){
                    if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && effectCloud.getSource() != player) {
                        event.setCancelled(true);
                        ((Player) effectCloud.getSource()).sendMessage(ChatColor.RED + "That player has PvP protection!");
                        return;
                    }
                }
            }
        }

        // Projectile Protection
        if (event.getDamager() instanceof Projectile){
            Projectile projectile = (Projectile) event.getDamager();

            if (!(this.plugin.getConfig().getBoolean("protection.combat.projectile"))){
                if (!(projectile instanceof ThrownPotion)) {
                    return;
                }
            }

            if (projectile instanceof ThrownPotion){
                if (!(this.plugin.getConfig().getBoolean("protection.potions.types.splash-potions"))){
                    return;
                }
                if (!(this.plugin.getConfig().getBoolean("protection.potions.effects.instant-damage"))){
                    return;
                }
            }

            if (projectile.getShooter() instanceof Player){
                Player player = (Player) event.getEntity();
                if (pvpPlayers.containsKey(((Player) projectile.getShooter()).getUniqueId())){
                    if ((pvpPlayers.get(((Player) projectile.getShooter()).getUniqueId()).hasProtection) && projectile.getShooter() != player){
                        event.setCancelled(true);
                        ((Player) projectile.getShooter()).sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                        return;
                    }
                }
                if (pvpPlayers.containsKey(player.getUniqueId())){
                    if ((pvpPlayers.get(player.getUniqueId()).hasProtection) && projectile.getShooter() != player) {
                        event.setCancelled(true);
                        ((Player) projectile.getShooter()).sendMessage(ChatColor.RED + "That player has PvP protection!");
                    }
                }
            }
        }
    }
}
