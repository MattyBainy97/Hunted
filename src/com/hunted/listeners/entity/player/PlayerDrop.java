package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDrop extends HListener{

    public PlayerDrop(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){

        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {

            e.setCancelled(true);

        }

    }

}
