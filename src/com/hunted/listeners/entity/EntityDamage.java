package com.hunted.listeners.entity;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage extends HListener{

    public EntityDamage(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){

        if(e.getEntity() instanceof Player) {

            Player p = ((Player) e.getEntity()).getPlayer();

            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL) || e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)){

                e.setCancelled(true);

            }

        }

    }

}
