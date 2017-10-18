package com.hunted.handlers.player;

import com.hunted.handlers.game.Hook;
import com.hunted.handlers.packets.Reflection;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.hunted.Hunted;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class PlayerHandler {

    private static List<UUID> crouched = new ArrayList<>();
    private static List<UUID> glowing = new ArrayList<>();

    private static List<UUID> online = new ArrayList<>();
    private static List<UUID> alive = new ArrayList<>();
    private static List<UUID> dead = new ArrayList<>();
    private static List<UUID> survivors = new ArrayList<>();
    private static List<UUID> incapacitated = new ArrayList<>();
    private static List<UUID> hanging = new ArrayList<>();

    private static List<UUID> onCooldown = new ArrayList<>();

    private static HashMap<UUID, PacketPlayOutBoss> bossBars = new HashMap<>();

    private static UUID murderer;

    public static void reset(){

        crouched.clear();
        glowing.clear();
        online.clear();
        alive.clear();
        dead.clear();
        survivors.clear();
        incapacitated.clear();
        hanging.clear();
        onCooldown.clear();
        murderer = null;

    }

    public static void addHanging(Player p){

        UUID uuid = p.getUniqueId();

        hanging.add(uuid);

    }

    public static void removeHanging(Player p){

        UUID uuid = p.getUniqueId();

        if(Hook.getHookFromPlayer(p) != null) {

            Hook h = Hook.getHookFromPlayer(p);
            h.removeHanging();
            removeBossBar(p);

        }

        hanging.remove(uuid);

    }

    public static void setOnCooldown(Player p){

        onCooldown.add(p.getUniqueId());

        Bukkit.getScheduler().runTaskLater(Hunted.getPlugin(), new Runnable() {

            @Override
            public void run() {

                onCooldown.remove(p.getUniqueId());

            }

        }, 40L);

    }

    public static boolean isOnCooldown(Player p){

        return onCooldown.contains(p.getUniqueId());

    }

    public static boolean isHanging(Player p){

        UUID uuid = p.getUniqueId();

        return hanging.contains(uuid);

    }

    public static void addOnline(Player p){

        UUID uuid = p.getUniqueId();

        online.add(uuid);

    }

    public static void addSurvivor(Player p){

        UUID uuid = p.getUniqueId();

        survivors.add(uuid);

    }

    public static void addIncapacitated(Player p){

        UUID uuid = p.getUniqueId();

        incapacitated.add(uuid);

    }

    public static void removeIncapacitated(Player p){

        UUID uuid = p.getUniqueId();

        incapacitated.remove(uuid);

    }

    public static boolean isIncapacitated(Player p){

        UUID uuid = p.getUniqueId();

        return incapacitated.contains(uuid);

    }

    public static List<UUID> getSurvivors(){

        return survivors;

    }

    public static void setPlayerName(Player p, String newName){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();
        GameProfile gp = ep.getProfile();

        try {

            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL);

            nameField.set(gp, newName);

        } catch (IllegalAccessException | NoSuchFieldException ex) {

            throw new IllegalStateException(ex);

        }

        reloadPlayer(p);

    }

    public static void setPlayerSkin(Player p, Murderer skin){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();
        GameProfile gp = ep.getProfile();
        PropertyMap pm = gp.getProperties();

        Property property = pm.get("textures").iterator().next();

        pm.remove("textures", property);
        pm.put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));

        reloadPlayer(p);

    }

    public static void sendBossBar(Player p){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();

        UUID uuid = UUID.randomUUID();
        IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\":\"§cHealth\"}");
        PacketPlayOutBoss bossBar = new PacketPlayOutBoss();

        Reflection.setValue(bossBar, "a", uuid);
        Reflection.setValue(bossBar, "b", PacketPlayOutBoss.Action.ADD);
        Reflection.setValue(bossBar, "c", title);
        Reflection.setValue(bossBar, "d", 1);
        Reflection.setValue(bossBar, "e", BossBattle.BarColor.RED);
        Reflection.setValue(bossBar, "f", BossBattle.BarStyle.NOTCHED_20);
        Reflection.setValue(bossBar, "g", true);
        Reflection.setValue(bossBar, "h", false);
        Reflection.setValue(bossBar, "i", false);

        ep.playerConnection.sendPacket(bossBar);

        bossBars.put(p.getUniqueId(), bossBar);

    }

    public static void updateBossBar(Player p, float health){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();

        PacketPlayOutBoss bossBar = bossBars.get(p.getUniqueId());

        Reflection.setValue(bossBar, "d", health);

        ep.playerConnection.sendPacket(bossBar);

    }

    public static float getCurrentBossHealth(Player p){

        PacketPlayOutBoss bossBar = bossBars.get(p.getUniqueId());

        float f = 0;

        try{

            f = (float) Reflection.getFieldValue(bossBar, "d");

        }catch (Exception e){

            e.printStackTrace();

        }

        return f;

    }

    public static void removeBossBar(Player p){

        EntityPlayer ep = ((CraftPlayer) p).getHandle();
        PacketPlayOutBoss bossBar = bossBars.get(p.getUniqueId());

        Reflection.setValue(bossBar, "b", PacketPlayOutBoss.Action.REMOVE);

        ep.playerConnection.sendPacket(bossBar);

        bossBars.remove(p.getUniqueId());

    }

    public static boolean hasBossBar(Player p){

        return bossBars.containsKey(p.getUniqueId());

    }

    public static void entityMeta(Entity entity, byte bitmask, Player sendTo){

        List list = new ArrayList();

        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata();

        DataWatcher.Item item = new DataWatcher.Item(DataWatcherRegistry.a.a(0), bitmask);

        list.add(item);

        try {

            Field a = PacketPlayOutEntityMetadata.class.getDeclaredField("a");
            a.setAccessible(true);
            a.set(packet, entity.getId());

            Field b = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
            b.setAccessible(true);
            b.set(packet, list);

        }catch (IllegalAccessException | NoSuchFieldException ex){

            throw new IllegalStateException(ex);

        }

        EntityPlayer entityPlayer = ((CraftPlayer) sendTo).getHandle();
        entityPlayer.playerConnection.sendPacket(packet);

    }

    public static void playerMeta(Player player, byte bitmask, Player sendTo){

        EntityPlayer ep = ((CraftPlayer)player).getHandle();

        List list = new ArrayList();

        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata();

        if(bitmask == (byte) 0x40){

            if(crouched.contains(player.getUniqueId())){

                bitmask = (byte) (0x02 | 0x40);

            }

            if(!glowing.contains(player.getUniqueId())){

                glowing.add(player.getUniqueId());

            }

        }else if(bitmask == (byte) 0x02){

            if(glowing.contains(player.getUniqueId())){

                bitmask = (byte) (0x02 | 0x40);

            }

            if(!crouched.contains(player.getUniqueId())){

                crouched.add(player.getUniqueId());

            }

        }else if(bitmask == (byte) 0){

            if(glowing.contains(player.getUniqueId())){

                glowing.remove(player.getUniqueId());

            }

            if(crouched.contains(player.getUniqueId())){

                crouched.remove(player.getUniqueId());

            }

        }else{

            if(!glowing.contains(player.getUniqueId())){

                glowing.add(player.getUniqueId());

            }

            if(!crouched.contains(player.getUniqueId())){

                crouched.add(player.getUniqueId());

            }

        }

        DataWatcher.Item item = new DataWatcher.Item(DataWatcherRegistry.a.a(0), bitmask);

        list.add(item);

        try {

            Field a = PacketPlayOutEntityMetadata.class.getDeclaredField("a");
            a.setAccessible(true);
            a.set(packet, player.getEntityId());

            Field b = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
            b.setAccessible(true);
            b.set(packet, list);

        }catch (IllegalAccessException | NoSuchFieldException ex){

            throw new IllegalStateException(ex);

        }

        EntityPlayer entityPlayer = ((CraftPlayer) sendTo).getHandle();
        entityPlayer.playerConnection.sendPacket(packet);

    }

    private static final void reloadPlayer(Player player) {

        final CraftPlayer cp = (CraftPlayer) player;
        final EntityPlayer ep = cp.getHandle();
        final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
        final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
        final PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(ep.dimension, ep.getWorld().getDifficulty(), ep.getWorld().getWorldData().getType(), ep.playerInteractManager.getGameMode());
        final PacketPlayOutHeldItemSlot slot = new PacketPlayOutHeldItemSlot(player.getInventory().getHeldItemSlot());
        ep.playerConnection.sendPacket(removeInfo);
        ep.playerConnection.sendPacket(addInfo);
        ep.playerConnection.sendPacket(respawn);
        ep.playerConnection.sendPacket(slot);
        cp.updateScaledHealth();
        ep.triggerHealthUpdate();
        cp.updateInventory();
        Bukkit.getScheduler().runTask(Hunted.getPlugin(), new Runnable(){

            @Override
            public void run() {

                ep.updateAbilities();

            }

        });

        for (Player p : Bukkit.getOnlinePlayers()) {

            p.hidePlayer(player);
            p.showPlayer(player);

        }

    }

    public static Player getMurderer(){

        return Bukkit.getPlayer(murderer);

    }

    public static Player chooseMurderer(){

        Random random = new Random();
        int min = 0;
        int max = online.size() - 1;

        int randomInt = random.nextInt((max - min) + 1) + min;

        murderer = online.get(randomInt);

        return Bukkit.getPlayer(murderer);

    }

    public static boolean isSurvivor(Player p){

        return survivors.contains(p.getUniqueId());

    }

    public static boolean isCrouching(Player p){

        UUID uuid = p.getUniqueId();

        if(crouched.contains(uuid)){

            return true;

        }

        return false;

    }

    public static void playerDeath(Player p) {

        UUID uuid = p.getUniqueId();

        if(isHanging(p)) {

            removeBossBar(p);
            hanging.remove(uuid);

        }

        dead.add(uuid);
        alive.remove(uuid);

    }
}