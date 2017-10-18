package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave extends HListener {

    public PlayerLeave(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        Player p = e.getPlayer();

        e.setQuitMessage("");

        ChatUtilities.broadcastServer(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GOLD + " left the server");

    }

}
