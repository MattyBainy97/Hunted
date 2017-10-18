package com.hunted.listeners.world;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RedstoneActivate extends HListener{

    public RedstoneActivate(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onRedstoneActivate(BlockRedstoneEvent e){

        if(e.getBlock().getType().equals(Material.REDSTONE_LAMP_ON) || e.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)){

            e.setNewCurrent(1);

        }

    }

}
