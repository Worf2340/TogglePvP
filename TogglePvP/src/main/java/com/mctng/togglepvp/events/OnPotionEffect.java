package com.mctng.togglepvp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import javax.swing.text.html.parser.Entity;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnPotionEffect implements Listener {

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event){
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityPotionEffectEvent.Cause.POTION_SPLASH) {
                if (pvpPlayers.containsKey(event.getEntity().getUniqueId())){
                    if (pvpPlayers.get(event.getEntity().getUniqueId()).hasProtection){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
