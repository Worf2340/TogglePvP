package com.mctng.togglepvp.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffectType;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnSplashPotion implements Listener {
    @EventHandler
    public void onSplashPotion(PotionSplashEvent event){
        PotionEffectType effectType = event.getEntity().getEffects().iterator().next().getType();
         if (effectType.getName().equals("POISON")){
            if (event.getEntity().getShooter() instanceof Player) {
                Player attacker = (Player) event.getEntity().getShooter();
                for (Entity e : event.getAffectedEntities()){
                    if (e instanceof Player) {
                        Player x = (Player) e;
                        System.out.println(x.getName() + ": " + x.getActivePotionEffects());
                    }
                }
            }
        }
    }
}
