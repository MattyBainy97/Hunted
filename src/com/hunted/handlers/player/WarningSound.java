package com.hunted.handlers.player;

import com.hunted.Hunted;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class WarningSound {

    private static HashMap<UUID, Type> playing = new HashMap<>();
    private static HashMap<UUID, Integer> threads = new HashMap<>();

    public static boolean isPlaying(Player p){

        UUID uuid = p.getUniqueId();

        return playing.containsKey(uuid);

    }

    public static void playSound(Player p, int timeBetween, int loopTime){

        UUID uuid = p.getUniqueId();

        if(loopTime == 30) {

            playing.put(uuid, Type.FAR);

        }else if(loopTime == 15) {

            playing.put(uuid, Type.MEDIUM);

        }else if(loopTime == 10) {

            playing.put(uuid, Type.CLOSE);

        }

        EntityPlayer ep = ((CraftPlayer) p).getHandle();

        String string = "block.note.basedrum";
        MinecraftKey key = new MinecraftKey(string);

        SoundEffect drum = SoundEffect.a.get(key);
        float volume = 1F;
        float pitch = 0.5F;

        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Hunted.getPlugin(), new Runnable(){

            @Override
            public void run() {

                Location l = p.getLocation();

                PacketPlayOutNamedSoundEffect beat1 = new PacketPlayOutNamedSoundEffect(drum, SoundCategory.BLOCKS, l.getX(), l.getY() + 1, l.getZ(), volume, pitch);
                ep.playerConnection.sendPacket(beat1);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Hunted.getPlugin(), new Runnable() {

                    @Override
                    public void run() {

                        Location l2 = p.getLocation();

                        PacketPlayOutNamedSoundEffect beat2 = new PacketPlayOutNamedSoundEffect(drum, SoundCategory.BLOCKS, l2.getX(), l2.getY() + 12, l.getZ(), volume, pitch);
                        ep.playerConnection.sendPacket(beat2);

                    }

                }, timeBetween);



            }

        }, 0L, timeBetween + loopTime);

        threads.put(uuid, task);

    }

    public static void stopSound(Player p){

        UUID uuid = p.getUniqueId();

        playing.remove(uuid);

        Bukkit.getScheduler().cancelTask(threads.get(uuid));

        threads.remove(uuid);

    }

    public static boolean isType(Player p, Type type){

        UUID uuid = p.getUniqueId();

        return playing.get(uuid) == type;

    }

    public enum Type{

        FAR, MEDIUM, CLOSE;

    }

}
