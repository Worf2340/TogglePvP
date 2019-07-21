package com.mctng.togglepvp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffectType;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnSplashPotion implements Listener {
    @EventHandler
    public void onSplashPotion(PotionSplashEvent event){
        System.out.println(event.getAffectedEntities());
        PotionEffectType effect = event.getPotion().getEffects().iterator().next().getType();
        System.out.println(effect.getName());

        pvpPlayers.forEach((UUID, pvpPlayer) -> {
            System.out.println(pvpPlayer.player);
            if (event.getAffectedEntities().contains(pvpPlayer.player)){
                System.out.println("jhhh");
                pvpPlayer.player.removePotionEffect(effect);
            }
        });
    }
}
