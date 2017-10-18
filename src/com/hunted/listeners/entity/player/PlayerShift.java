package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerShift extends HListener{

    public PlayerShift(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerShiftEvent(PlayerToggleSneakEvent e){

        e.setCancelled(true);

    }

}
