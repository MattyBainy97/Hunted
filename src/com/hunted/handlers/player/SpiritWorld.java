package com.hunted.handlers.player;

import com.hunted.Hunted;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

public class SpiritWorld {

    private static boolean onCooldown = false;
    private static boolean inSpiritWorld = false;

    public static void playSound(Location l){

        onCooldown = true;

        l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.85F);
        l.getWorld().playSound(l, Sound.BLOCK_NOTE_BASEDRUM, 1.0F, 0.85F);
        l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.6666F);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Hunted.getPlugin(), new Runnable() {

            @Override
            public void run() {

                l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.8F);
                l.getWorld().playSound(l, Sound.BLOCK_NOTE_BASEDRUM, 1.0F, 0.8F);
                l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.6333F);

            }

        }, 10L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Hunted.getPlugin(), new Runnable() {

            @Override
            public void run() {

                l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.85F);
                l.getWorld().playSound(l, Sound.BLOCK_NOTE_BASEDRUM, 1.0F, 0.85F);
                l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.6666F);

                l.getWorld().playSound(l, Sound.ENTITY_VEX_AMBIENT, 3.0F, 1.0F);

            }

        }, 20L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Hunted.getPlugin(), new Runnable() {

            @Override
            public void run() {

                l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.7F);
                l.getWorld().playSound(l, Sound.BLOCK_NOTE_BASEDRUM, 1.0F, 0.7F);
                l.getWorld().playSound(l, Sound.BLOCK_NOTE_PLING, 1.0F, 0.533333F);

            }

        }, 30L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Hunted.getPlugin(), new Runnable() {

            @Override
            public void run() {

                onCooldown = false;

            }

        }, 40L);

    }

    public static boolean isOnCooldown(){

        return onCooldown;

    }

    public static boolean isInSpiritWorld(){

        return inSpiritWorld;

    }

    public static void setInSpiritWorld(boolean b){

        inSpiritWorld = b;

    }

}
