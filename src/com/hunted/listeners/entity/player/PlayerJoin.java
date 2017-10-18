package com.hunted.listeners.entity.player;

import com.hunted.GameState;
import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import com.hunted.utils.LocationUtilities;
import net.minecraft.server.v1_11_R1.*;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;

public class PlayerJoin extends HListener {
    
    public PlayerJoin(Hunted pl) {

        super(pl);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent e) {

        e.setJoinMessage("");
        final Player p = e.getPlayer();

        CraftPlayer craftplayer = (CraftPlayer) p;

        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent header = ChatSerializer.a("{\"text\": \"   §4Hunted §9v1   \"}");
        IChatBaseComponent footer = ChatSerializer.a("{\"text\": \"   §4Development Test   \"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try {
            
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, header);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footer);
            footerField.setAccessible(!footerField.isAccessible());
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }

        connection.sendPacket(packet);

        ChatUtilities.broadcastServer(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GOLD + " joined the server");

        if (GameState.isState(GameState.IN_LOBBY)) {

            LocationUtilities.spawnLobby(p);
            p.getInventory().clear();
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.setHealth(20.0);
            p.setFoodLevel(40);
            p.setExp(0);
            p.setLevel(0);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.setGameMode(GameMode.ADVENTURE);

        } else {
            
            //todo

        }

    }
    
}