package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import com.hunted.utils.ScoreboardUtilities;
import net.minecraft.server.v1_11_R1.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath extends HListener{

    public PlayerDeath(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){

        e.setDeathMessage("");
        e.getDrops().clear();
        e.setDroppedExp(0);
        Player p = e.getEntity().getPlayer();

        PlayerHandler.playerDeath(p);

        ChatUtilities.broadcast(ChatColor.YELLOW + p.getName() + ChatColor.GOLD + " was sacrificed");

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Hunted.getPlugin(), new Runnable() {

            @Override
            public void run() {

                ((CraftPlayer) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));

            }

        }, 5L);

    }

}
