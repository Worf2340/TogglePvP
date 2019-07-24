package com.mctng.togglepvp.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mctng.togglepvp.TogglePvP.protectedPotions;
import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnLingeringPotionEffect implements Listener {
    @EventHandler
    public void onLingeringPotion(AreaEffectCloudApplyEvent event){


        PotionEffectType effectType = event.getEntity().getBasePotionData().getType().getEffectType();
        if (protectedPotions.contains(effectType.getName())){
            if (event.getEntity().getSource() instanceof Player) {
                Player attacker = (Player) event.getEntity().getSource();

                ArrayList<Entity> removeEntities = new ArrayList<>();

                // Cancel all affected players if the attacker has pvp protection
                if (pvpPlayers.containsKey(attacker.getUniqueId())){
                    if (pvpPlayers.get(attacker.getUniqueId()).hasProtection) {
                        for (Entity e : event.getAffectedEntities()){
                            if (e instanceof Player){
                                if (e != attacker) {
                                    removeEntities.add(e);
                                    //attacker.sendMessage(ChatColor.RED + "You can't attack players while you have PvP protection!");
                                }
                            }
                        }
                        for (Entity e : removeEntities){
                            event.getAffectedEntities().remove(e);
                        }

                    }
                }

                // Cancel event for players with pvp protection
                for (Entity e : event.getAffectedEntities()) {
                    if (e instanceof Player) {
                        if (pvpPlayers.containsKey(e.getUniqueId())) {
                            if (pvpPlayers.get(e.getUniqueId()).hasProtection) {
                                removeEntities.add(e);
                                //attacker.sendMessage(ChatColor.RED + "That player has PvP protection!");
                            }
                        }
                    }
                }
                for (Entity e : removeEntities){
                    event.getAffectedEntities().remove(e);
                }

            }
        }
    }
}
