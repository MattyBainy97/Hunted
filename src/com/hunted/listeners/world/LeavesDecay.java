package com.hunted.listeners.world;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecay extends HListener{

    public LeavesDecay(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e){

        e.setCancelled(true);

    }

}
