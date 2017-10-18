package com.hunted.listeners.world;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends HListener {

    public BlockBreak(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {

            e.setCancelled(true);

        }

    }

}
