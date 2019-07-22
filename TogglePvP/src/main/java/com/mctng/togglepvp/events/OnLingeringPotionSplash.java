package com.mctng.togglepvp.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

// Only handles warning attackers about PvP protection
public class OnLingeringPotionSplash implements Listener {
    @EventHandler
    public void onLingeringPotionSplash (LingeringPotionSplashEvent event){
        String[] protectedPotions = {"POISON", "SLOW", "WEAKNESS"};

        PotionEffectType effectType = event.getEntity().getEffects().iterator().next().getType();
        if (Arrays.asList(protectedPotions).contains(effectType.getName())){
            if (event.getAreaEffectCloud().getSource() instanceof Player) {
                Player attacker = (Player) event.getAreaEffectCloud().getSource();
                double coord = (event.getAreaEffectCloud().getRadius())/2;

                // Warn the attacker if they have PvP protection
                if (pvpPlayers.containsKey(attacker.getUniqueId())){
                    if (pvpPlayers.get(attacker.getUniqueId()).hasProtection) {
                        for (Entity e : event.getAreaEffectCloud().getNearbyEntities(coord, coord, coord)){
                            if (e instanceof Player){
                                if (e != attacker) {
                                    attacker.sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                                }
                            }
                        }
                        return;
                    }
                }

                // Cancel event for players with pvp protection
                for (Entity e : event.getAreaEffectCloud().getNearbyEntities(coord,coord,coord)) {
                    if (e instanceof Player) {
                        if (pvpPlayers.containsKey(e.getUniqueId())) {
                            if (pvpPlayers.get(e.getUniqueId()).hasProtection) {
                                attacker.sendMessage(ChatColor.RED + "That player has PvP protection!");
                            }
                        }
                    }
                }
            }
        }
    }
}
