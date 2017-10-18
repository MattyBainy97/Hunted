package com.hunted.listeners.server;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class ListPing extends HListener{

    public ListPing(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onListPing(ServerListPingEvent e){

        e.setMotd(ChatColor.GRAY + "« " + ChatColor.RED + "HUNTED" + ChatColor.GRAY + " »\n" + ChatColor.GRAY + "« " + ChatColor.BLUE + "DEVELOPMENT SERVER" + ChatColor.GRAY + " »");

    }

}
