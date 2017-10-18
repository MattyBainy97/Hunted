package com.hunted.listeners.entity;

import com.hunted.Hunted;
import com.hunted.handlers.game.HItem;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.handlers.player.SpiritWorld;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class EntityDamageByEntity extends HListener{

    public EntityDamageByEntity(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){

        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){

            Player damaged = ((Player) e.getEntity()).getPlayer();
            Player damager = ((Player) e.getDamager()).getPlayer();

            if(damager.equals(PlayerHandler.getMurderer()) && !PlayerHandler.isIncapacitated(damaged) && !PlayerHandler.isHanging(damaged)) {

                ItemStack itemInHand = damager.getEquipment().getItemInMainHand();
                Material inHandMat = itemInHand.getType();

                if (inHandMat.equals(Material.IRON_AXE) || inHandMat.equals(Material.BONE) || inHandMat.equals(Material.IRON_HOE)) {

                    damager.getInventory().getItemInMainHand().setDurability((short) 0);

                    if (SpiritWorld.isInSpiritWorld() || SpiritWorld.isOnCooldown()) {

                        e.setCancelled(true);

                        ChatUtilities.onePlayer("You cannot attack from the spirit world or when entering/leaving the spirit world", damager);

                    } else {

                        e.setDamage(0D);

                        createBlood(damaged);

                        if (!PlayerHandler.isCrouching(damaged)) {

                            for (Player p : Bukkit.getOnlinePlayers()) {

                                if (p != damaged) {

                                    PlayerHandler.playerMeta(damaged, (byte) 0x02, p);

                                }

                            }

                            damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 0, false, false));

                            damaged.setHealth(10.0);

                        } else {

                            ChatUtilities.playTitle(damager, ChatColor.DARK_RED + "INCAPACITATED", 10, 40, 10);
                            ChatUtilities.playSubtitle(damager, ChatColor.WHITE + "Right click the survivor to pick them up", 10, 40, 10);

                            ChatUtilities.playTitle(damaged, ChatColor.DARK_RED + "INCAPACITATED", 10, 40, 10);
                            ChatUtilities.playSubtitle(damaged, ChatColor.WHITE + "A survivor can heal you if you crawl to them", 10, 40, 10);
                            damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 4, false, false));

                            PlayerHandler.addIncapacitated(damaged);

                            damaged.setHealth(1.0);

                            for (UUID uuid : PlayerHandler.getSurvivors()) {

                                Player p = Bukkit.getPlayer(uuid);

                                if (p != damaged) {

                                    PlayerHandler.playerMeta(damaged, (byte) (0x02 | 0x40), p);

                                } else {

                                    PlayerHandler.playerMeta(damaged, (byte) (0x02 | 0x40), damager);

                                }

                            }

                        }

                    }

                }else{

                    e.setCancelled(true);

                }

            } else {

                e.setCancelled(true);

            }

        }else{

            e.setCancelled(true);

        }

    }

    private void createBlood(Entity damaged) {

        Location loc = damaged.getLocation();

        for(double y = 0.5; y <= 1.5; y+=0.05) {

            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) loc.getX(), (float) (loc.getY() + y), (float) loc.getZ(), 0, 0, 0, 0, 1);

            for(Player p : Bukkit.getOnlinePlayers()) {

                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);

            }

        }

    }

}
