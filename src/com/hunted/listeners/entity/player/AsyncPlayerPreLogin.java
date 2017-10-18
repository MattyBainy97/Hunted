package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import java.util.UUID;

public class AsyncPlayerPreLogin extends HListener {

    public AsyncPlayerPreLogin(Hunted pl) {
        super(pl);
    }

    @EventHandler
    public void playerPreLogin(AsyncPlayerPreLoginEvent e) {

        if (    e.getUniqueId().equals(UUID.fromString("bc61d2bf-3bcc-4058-a610-60eaa62c14e0")) ||
                e.getUniqueId().equals(UUID.fromString("a2556581-f47e-4cfe-ae4b-c79c46b531c8")) ||
                e.getUniqueId().equals(UUID.fromString("822d2d09-39fb-44ca-9706-b8874de57cd7")) ||
                e.getUniqueId().equals(UUID.fromString("d2d56e7-b78e-4dee-86bb-4ef66dcfd484")) ||
                e.getUniqueId().equals(UUID.fromString("8e75f292-f615-4d41-b599-bd7336a7ad63")) ||
                e.getUniqueId().equals(UUID.fromString("19eab77b-2b5c-497b-89e2-68e580b1ae9a")) ||
                e.getUniqueId().equals(UUID.fromString("61b98db6-34b7-485f-888f-39b136d6f0e7")) ||
                e.getUniqueId().equals(UUID.fromString("78f6db27-ce66-4899-b7fc-068d83ed1548"))) {

            e.allow();

        } else {

            Player matty = Bukkit.getPlayer(UUID.fromString("bc61d2bf-3bcc-4058-a610-60eaa62c14e0"));
            Player women = Bukkit.getPlayer(UUID.fromString("a2556581-f47e-4cfe-ae4b-c79c46b531c8"));
            Player minecraft = Bukkit.getPlayer(UUID.fromString("822d2d09-39fb-44ca-9706-b8874de57cd7"));

            if(     Bukkit.getOnlinePlayers().contains(matty) ||
                    Bukkit.getOnlinePlayers().contains(women) ||
                    Bukkit.getOnlinePlayers().contains(minecraft)) {

                e.allow();

            }else{

                e.disallow(Result.KICK_OTHER, ChatColor.RED + "You are temporarily denied from joining this server.\nTry again later.");

            }
            
        }

    }
}