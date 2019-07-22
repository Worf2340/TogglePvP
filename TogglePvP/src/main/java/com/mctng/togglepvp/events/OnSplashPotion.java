package com.mctng.togglepvp.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnSplashPotion implements Listener {
    @EventHandler

    public void onSplashPotion(PotionSplashEvent event){

        String[] protectedPotions = {"POISON", "SLOW", "WEAKNESS"};

        PotionEffectType effectType = event.getEntity().getEffects().iterator().next().getType();
         if (Arrays.asList(protectedPotions).contains(effectType.getName())){
            if (event.getEntity().getShooter() instanceof Player) {
                Player attacker = (Player) event.getEntity().getShooter();

                // Cancel all affected players if the attacker has pvp protection
                if (pvpPlayers.containsKey(attacker.getUniqueId())){
                    if (pvpPlayers.get(attacker.getUniqueId()).hasProtection) {
                        for (Entity e : event.getAffectedEntities()){
                            if (e instanceof Player){
                                if (e != attacker) {
                                    event.setIntensity((Player) e, 0);
                                    attacker.sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                                }
                            }
                        }
                        return;
                    }
                }

                // Cancel event for players with pvp protection
                for (Entity e : event.getAffectedEntities()) {
                    if (e instanceof Player) {
                        if (pvpPlayers.containsKey(e.getUniqueId())) {
                            if (pvpPlayers.get(e.getUniqueId()).hasProtection) {
                                event.setIntensity((Player) e, 0);
                                attacker.sendMessage(ChatColor.RED + "That player has PvP protection!");
                            }
                        }
                    }
                }
            }
        }
    }
}
