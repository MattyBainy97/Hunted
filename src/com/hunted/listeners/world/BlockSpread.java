package com.hunted.listeners.world;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockSpread extends HListener {

    public BlockSpread(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e){

        e.setCancelled(true);

    }

}

