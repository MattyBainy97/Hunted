package com.hunted.listeners.world;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockGrowEvent;

public class BlockGrow extends HListener{

    public BlockGrow(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent e){

        e.setCancelled(true);

    }

}
