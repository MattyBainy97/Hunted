package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class PlayerSprint extends HListener{

    public PlayerSprint(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerSprintEvent(PlayerToggleSprintEvent e){

        e.setCancelled(true);

    }

}
