package com.hunted.handlers.player;

import net.minecraft.server.v1_11_R1.*;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class FakePlayer {

    public int id;
    Location l;

    private static HashMap<UUID, FakePlayer> fakePlayers = new HashMap<>();

    private void setPrivateField(Class type, Object object, String name, Object value) {

        try {

            Field f = type.getDeclaredField(name);
            f.setAccessible(true);
            f.set(object, value);
            f.setAccessible(false);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public FakePlayer(EntityHuman h) {

        PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(h);
        int id = new Random().nextInt(5000 - 1000) + 1000;
        setPrivateField(PacketPlayOutNamedEntitySpawn.class, spawn, "a", id);
        this.id = id;
        if(fakePlayers.containsKey(h.getUniqueID())){

            fakePlayers.get(h.getUniqueID()).remove();

        }
        fakePlayers.put(h.getUniqueID(), this);
        for (Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawn);

        }

    }

    public void teleport(Location loc) {

        PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport();
        PacketPlayOutEntityHeadRotation headPacket = new PacketPlayOutEntityHeadRotation();

        setPrivateField(PacketPlayOutEntityTeleport.class, tp, "a", id);
        setPrivateField(PacketPlayOutEntityTeleport.class, tp, "b", (loc.getX()));
        setPrivateField(PacketPlayOutEntityTeleport.class, tp, "c", (loc.getY()));
        setPrivateField(PacketPlayOutEntityTeleport.class, tp, "d", (loc.getZ()));
        setPrivateField(PacketPlayOutEntityTeleport.class, tp, "e", getCompressedAngle(loc.getYaw()));
        setPrivateField(PacketPlayOutEntityTeleport.class, tp, "f", getCompressedAngle(loc.getPitch()));

        setPrivateField(PacketPlayOutEntityHeadRotation.class, headPacket, "a", id);
        setPrivateField(PacketPlayOutEntityHeadRotation.class, headPacket, "b", getCompressedAngle(loc.getYaw()));

        this.l = loc;

        for (Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(tp);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(headPacket);

        }

    }

    public void sleep() {

        PacketPlayOutBed bedPacket = new PacketPlayOutBed();

        setPrivateField(PacketPlayOutBed.class, bedPacket, "a", id);
        setPrivateField(PacketPlayOutBed.class, bedPacket, "b", new BlockPosition(l.getBlockX(), l.getBlockY() - 2, l.getBlockZ()));

        PacketPlayOutRelEntityMove movePacket = new PacketPlayOutRelEntityMove(id, (byte) 0, (byte) (-60.8), (byte) 0, false);

        for(Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bedPacket);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(movePacket);

        }

    }

    private byte getCompressedAngle(float value) {

        return (byte) ((value * 256.0F) / 360.0F);

    }

    public void remove() {

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(id);

        for (Player p : Bukkit.getOnlinePlayers()) {

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        }

    }

    public double getX() {

        return l.getX();

    }

    public double getY() {

        return l.getY();

    }

    public double getZ() {

        return l.getZ();

    }

    public Location getLocation() {

        return l;

    }

}
