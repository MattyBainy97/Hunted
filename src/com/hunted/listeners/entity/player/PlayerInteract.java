package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.handlers.game.HItem;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.handlers.player.SpiritWorld;
import com.hunted.handlers.player.Trap;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteract extends HListener {

    public PlayerInteract(Hunted pl){

        super(pl);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){

        Player p = e.getPlayer();

        if(e.getHand() == EquipmentSlot.HAND) {

            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

                if(p == PlayerHandler.getMurderer()) {

                    if (p.getPassengers().isEmpty()) {

                        e.setCancelled(true);

                        if (p.getEquipment().getItemInMainHand().equals(HItem.getRealmCrosserWorld())) {

                            if (SpiritWorld.isOnCooldown()) {

                                ChatUtilities.onePlayer("Item is on cooldown", p);

                            } else {

                                p.getInventory().remove(HItem.getRealmCrosserWorld());
                                p.getInventory().setItem(1, HItem.getRealmCrosserSpirit());

                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));

                                SpiritWorld.playSound(p.getLocation().add(0, 1, 0));
                                SpiritWorld.setInSpiritWorld(true);

                                for (Player player : Bukkit.getOnlinePlayers()) {

                                    if (player != p) {

                                        player.hidePlayer(p);

                                    }

                                }

                            }

                        } else if (p.getEquipment().getItemInMainHand().equals(HItem.getRealmCrosserSpirit())) {

                            if (SpiritWorld.isOnCooldown()) {

                                ChatUtilities.onePlayer("Item is on cooldown", p);

                            } else {

                                p.getInventory().remove(HItem.getRealmCrosserSpirit());
                                p.getInventory().setItem(1, HItem.getRealmCrosserWorld());

                                p.removePotionEffect(PotionEffectType.SPEED);

                                SpiritWorld.playSound(p.getLocation().add(0, 1, 0));
                                SpiritWorld.setInSpiritWorld(false);

                                for (Player player : Bukkit.getOnlinePlayers()) {

                                    if (player != p) {

                                        player.showPlayer(p);

                                    }

                                }

                            }

                        } else if (p.getEquipment().getItemInMainHand().equals(HItem.getTrapperTrap())) {

                            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                                Block trap = p.getWorld().getBlockAt(e.getClickedBlock().getLocation().add(0, 1, 0));
                                trap.setType(Material.STONE_PLATE);
                                Trap.setTrap(trap);

                                p.getInventory().remove(HItem.getTrapperTrap());

                                ChatUtilities.onePlayer("Trap Placed at: " + ChatColor.AQUA + "X- " + trap.getX() + " Y- " + trap.getY() + " Z- " + trap.getZ(), p);

                            } else {

                                e.setCancelled(true);

                            }

                        } else if (p.getEquipment().getItemInMainHand().equals(HItem.getHillbillyFrenzy())) {

                            p.getWorld().playSound(p.getLocation().add(0, 1, 0), Sound.ENTITY_ENDERDRAGON_GROWL, 1.0F, 1.0F);

                            ChatUtilities.onePlayer(ChatColor.RED + "FRENZY ACTIVATED", p);

                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2, false, false));

                        }

                        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                            if (e.getClickedBlock().getType().equals(Material.SPRUCE_DOOR)) {


                            } else if (e.getClickedBlock().getType().equals(Material.DARK_OAK_DOOR)) {


                            } else if (e.getClickedBlock().getType().equals(Material.STONE_PLATE)) {

                                e.getClickedBlock().setType(Material.AIR);
                                Trap.removeTrap();

                                p.getInventory().setItem(1, HItem.getTrapperTrap());

                                ChatUtilities.onePlayer("Trap picked up", p);

                            } else {

                                e.setCancelled(true);

                            }

                        }

                    }else{

                        ChatUtilities.onePlayer("Cannot use ability while carrying a survivor", p);

                    }

                }else{

                    if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                        if (e.getClickedBlock().getType().equals(Material.CHEST)) {


                        } else if (e.getClickedBlock().getType().equals(Material.SPRUCE_DOOR)) {


                        } else if (e.getClickedBlock().getType().equals(Material.DARK_OAK_DOOR)) {


                        } else if (p.getGameMode().equals(GameMode.CREATIVE)) {


                        } else {

                            e.setCancelled(true);

                        }

                    }

                }

            } else {



            }

        }

    }

}
