package com.mctng.togglepvp.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;

import static com.mctng.togglepvp.TogglePvP.pvpPlayers;

public class OnLingeringPotion implements Listener {
    @EventHandler
    public void onLingeringPotion(AreaEffectCloudApplyEvent event){
        for (LivingEntity entity : event.getAffectedEntities()){
            if (pvpPlayers.containsKey(entity.getUniqueId())){

            }
        }
    }
}
