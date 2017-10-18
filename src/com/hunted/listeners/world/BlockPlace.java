package com.hunted.listeners.world;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends HListener {

    public BlockPlace(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {

            e.setCancelled(true);

        }

    }

}
