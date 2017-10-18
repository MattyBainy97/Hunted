package com.hunted.listeners.entity;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegen extends HListener{

    public EntityRegen(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onEntityRegen(EntityRegainHealthEvent e){

        if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED || e.getRegainReason() == EntityRegainHealthEvent.RegainReason.REGEN) {

            e.setCancelled(true);

        }

    }

}
