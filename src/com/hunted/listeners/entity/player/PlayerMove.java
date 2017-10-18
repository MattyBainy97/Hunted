package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.handlers.game.Hook;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.handlers.player.Trap;
import com.hunted.handlers.player.WarningSound;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_11_R1.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.Warning;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.reflect.Field;

public class PlayerMove extends HListener{

    public PlayerMove(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Player p = e.getPlayer();
        Player murderer = PlayerHandler.getMurderer();

        if(p == murderer){

            Block b = murderer.getWorld().getBlockAt(murderer.getLocation().subtract(0,1,0));

            if(Hook.getHookFromTrigger(b) != null){

                Hook h = Hook.getHookFromTrigger(b);

                if(!murderer.getPassengers().isEmpty() && !h.isTaken()){

                    for(Entity entity : murderer.getPassengers()){

                        murderer.removePassenger(entity);

                        for(Entity asEnt : entity.getPassengers()){

                            entity.removePassenger(asEnt);

                            if(asEnt instanceof Player){

                                Player player = (Player) asEnt;

                                for(Player pl : Bukkit.getOnlinePlayers()){

                                    if(pl != player) {

                                        PlayerHandler.playerMeta(player, (byte) 0, pl);
                                        PlayerHandler.playerMeta(player, (byte) 0x40, pl);

                                    }


                                }

                                ChatUtilities.broadcast(ChatColor.YELLOW + player.getName() + " has been hooked");

                                ChatUtilities.playTitle(murderer, ChatColor.DARK_RED + "Player Hooked", 10, 40, 10);
                                ChatUtilities.playSubtitle(murderer, ChatColor.WHITE + "Player will soon be sacrificed", 10, 40, 10);

                                PlayerHandler.sendBossBar(player);
                                h.getHangingTimer().startTimer();
                                h.setHanging(player);

                                if(Hook.getTimesHooked(player) == 2){

                                    PlayerHandler.updateBossBar(player, 0.5F);

                                }else if(Hook.getTimesHooked(player) == 3){

                                    PlayerHandler.updateBossBar(player, 0);

                                }

                            }

                        }

                        entity.remove();

                    }

                }

            }

        }else{

            if(PlayerHandler.isHanging(p)){

                if(e.getTo().getX() != e.getFrom().getX() || e.getTo().getY() != e.getFrom().getY() || e.getTo().getZ() != e.getFrom().getZ()) {

                    p.teleport(e.getFrom());
                    p.setFlying(true);

                }

            }

        }

        Block b = p.getWorld().getBlockAt(p.getLocation());

        if(Trap.isSet()){

            if(b.equals(Trap.getBlock())){



            }

        }

    }

}
