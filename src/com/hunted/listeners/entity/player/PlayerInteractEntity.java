package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.handlers.game.Generator;
import com.hunted.handlers.game.HItem;
import com.hunted.handlers.game.HealTimer;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.handlers.player.SpiritWorld;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractEntity extends HListener {

    public PlayerInteractEntity(Hunted pl) {

        super(pl);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent e){

        if(e.getHand() == EquipmentSlot.HAND) {

            if (e.getRightClicked() instanceof ArmorStand) {

                ArmorStand as = (ArmorStand) e.getRightClicked();

                if (Generator.getGeneratorFromArmorStand(as) != null && e.getPlayer() != PlayerHandler.getMurderer() && !PlayerHandler.isIncapacitated(e.getPlayer())) {

                    Generator generator = Generator.getGeneratorFromArmorStand(as);

                    if (generator.getPercentage() < 100) {

                        generator.setPercentage(generator.getPercentage() + 0.25);
                        generator.playChargingSound();

                    } else {

                        generator.generatorComplete();
                        Generator.removeGenerator(generator);

                    }

                    if (generator.getPercentage() > 0 && generator.getPercentage() < 10) {

                        as.setCustomName(ChatColor.DARK_GRAY + "██████████");

                    } else if (generator.getPercentage() >= 10 && generator.getPercentage() < 20) {

                        as.setCustomName(ChatColor.DARK_GREEN + "█" + ChatColor.DARK_GRAY + "█████████");

                    } else if (generator.getPercentage() >= 20 && generator.getPercentage() < 30) {

                        as.setCustomName(ChatColor.DARK_GREEN + "██" + ChatColor.DARK_GRAY + "████████");

                    } else if (generator.getPercentage() >= 30 && generator.getPercentage() < 40) {

                        as.setCustomName(ChatColor.DARK_GREEN + "███" + ChatColor.DARK_GRAY + "███████");

                    } else if (generator.getPercentage() >= 40 && generator.getPercentage() < 50) {

                        as.setCustomName(ChatColor.DARK_GREEN + "████" + ChatColor.DARK_GRAY + "██████");

                    } else if (generator.getPercentage() >= 50 && generator.getPercentage() < 60) {

                        as.setCustomName(ChatColor.DARK_GREEN + "█████" + ChatColor.DARK_GRAY + "█████");

                    } else if (generator.getPercentage() >= 60 && generator.getPercentage() < 70) {

                        as.setCustomName(ChatColor.DARK_GREEN + "██████" + ChatColor.DARK_GRAY + "████");

                    } else if (generator.getPercentage() >= 70 && generator.getPercentage() < 80) {

                        as.setCustomName(ChatColor.DARK_GREEN + "███████" + ChatColor.DARK_GRAY + "███");

                    } else if (generator.getPercentage() >= 80 && generator.getPercentage() < 90) {

                        as.setCustomName(ChatColor.DARK_GREEN + "████████" + ChatColor.DARK_GRAY + "██");

                    } else if (generator.getPercentage() >= 90 && generator.getPercentage() < 100) {

                        as.setCustomName(ChatColor.DARK_GREEN + "█████████" + ChatColor.DARK_GRAY + "█");

                    } else {

                        as.setCustomName(ChatColor.DARK_GREEN + "██████████");

                    }

                }

            } else if (e.getRightClicked() instanceof Player) {

                Player clicker = e.getPlayer();
                Player clicked = (Player) e.getRightClicked();

                if (clicker.equals(PlayerHandler.getMurderer()) && PlayerHandler.isIncapacitated(clicked) && !SpiritWorld.isInSpiritWorld() && !SpiritWorld.isOnCooldown() && clicker.getPassengers().isEmpty() && !clicker.getEquipment().getItemInMainHand().equals(HItem.getRealmCrosserSpirit()) && !clicker.getEquipment().getItemInMainHand().equals(HItem.getRealmCrosserWorld())) {

                    ArmorStand holder = (ArmorStand) clicker.getWorld().spawnEntity(clicker.getLocation(), EntityType.ARMOR_STAND);
                    holder.setVisible(false);
                    holder.setSmall(true);
                    holder.setInvulnerable(true);

                    clicker.addPassenger(holder);
                    holder.addPassenger(clicked);

                    ChatUtilities.playTitle(clicked, ChatColor.RED + "Picked Up", 10, 40, 10);
                    ChatUtilities.playSubtitle(clicked, ChatColor.WHITE + "Click [null] to attempt escape", 10, 40, 10);

                    ChatUtilities.playTitle(clicker, ChatColor.RED + "Picked Up", 10, 40, 10);
                    ChatUtilities.playSubtitle(clicker, ChatColor.WHITE + "Take them to a hook for sacrificing", 10, 40, 10);

                } else if (PlayerHandler.isSurvivor(clicked) && PlayerHandler.isSurvivor(clicker)) {

                    if (PlayerHandler.isHanging(clicked)) {

                        ChatUtilities.playTitle(clicked, ChatColor.GREEN + "RESCUED", 10, 40, 10);
                        ChatUtilities.playSubtitle(clicked, ChatColor.WHITE + "You have been saved", 10, 40, 10);

                        ChatUtilities.playTitle(clicker, ChatColor.GREEN + "RESCUED", 10, 40, 10);
                        ChatUtilities.playSubtitle(clicker, ChatColor.WHITE + "You saved " + ChatColor.YELLOW + clicked.getName(), 10, 40, 10);

                        PlayerHandler.removeHanging(clicked);

                        for (Player p : Bukkit.getOnlinePlayers()) {

                            if (p != clicked) {

                                PlayerHandler.playerMeta(clicked, (byte) 0, p);
                                PlayerHandler.playerMeta(clicked, (byte) 0x02, p);

                            }

                        }

                        clicked.setHealth(10.0);

                    } else if (PlayerHandler.isCrouching(clicked) && !PlayerHandler.isHanging(clicked) && !clicked.isInsideVehicle() && !PlayerHandler.isOnCooldown(clicked)) {

                        ChatUtilities.playTitle(clicked, ChatColor.GREEN + "HEALING", 0, 40, 10);

                        ChatUtilities.playTitle(clicker, ChatColor.GREEN + "HEALING", 0, 40, 10);

                        HealTimer h;

                        if (HealTimer.hasHealTimer(clicked)) {

                            h = HealTimer.getHealTimerFromPlayer(clicked);

                        } else {

                            h = new HealTimer(clicked);

                        }


                        if (h.getTime() > 50) {

                            h.setTime(0);
                            clicked.setHealth(20.0);

                            clicked.removePotionEffect(PotionEffectType.SLOW);

                            if (PlayerHandler.isIncapacitated(clicked)) {

                                PlayerHandler.removeIncapacitated(clicked);

                            }

                            for (Player p : Bukkit.getOnlinePlayers()) {

                                if (p != clicked) {

                                    PlayerHandler.playerMeta(clicked, (byte) 0, p);

                                }

                            }

                            ChatUtilities.playTitle(clicked, ChatColor.GREEN + "HEALED", 10, 40, 10);
                            ChatUtilities.playSubtitle(clicked, ChatColor.WHITE + "You were healed by " + ChatColor.YELLOW + clicker.getName(), 10, 40, 10);

                            ChatUtilities.playTitle(clicker, ChatColor.GREEN + "HEALED", 10, 40, 10);
                            ChatUtilities.playSubtitle(clicker, ChatColor.WHITE + "You healed " + ChatColor.YELLOW + clicked.getName(), 10, 40, 10);

                            clicker.getWorld().playSound(clicker.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

                        } else {

                            h.setTime(h.getTime() + 1);

                            double percentD = (h.getTime() / 50.0) * 100.0;
                            int percent = (int) percentD;

                            ChatUtilities.playSubtitle(clicked, ChatColor.WHITE + "You are being healed by " + ChatColor.YELLOW + clicker.getName() + " " + ChatColor.GREEN + percent + "%", 0, 40, 10);
                            ChatUtilities.playSubtitle(clicker, ChatColor.WHITE + "You are healing " + ChatColor.YELLOW + clicked.getName() + " " + ChatColor.GREEN + percent + "%", 0, 40, 10);

                        }


                    }

                }

            }

        }

    }

}
