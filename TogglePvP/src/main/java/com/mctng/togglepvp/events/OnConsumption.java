package com.mctng.togglepvp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class OnConsumption implements Listener {
    @EventHandler
    public void onConsumption(PlayerItemConsumeEvent event){
        System.out.println("hhh");
    }
}
