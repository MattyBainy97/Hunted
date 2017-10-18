package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwitchHand extends HListener {

    public PlayerSwitchHand(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerSwitchHand(PlayerSwapHandItemsEvent e){

        e.setCancelled(true);

    }

}
