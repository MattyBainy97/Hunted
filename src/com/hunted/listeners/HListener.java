package com.hunted.listeners;

import com.hunted.Hunted;
import net.minecraft.server.v1_11_R1.PacketPlayInUseEntity;
import org.bukkit.event.Listener;

public class HListener implements Listener{

    private Hunted plugin;

    protected HListener(Hunted pl){

        plugin = pl;

    }

}
