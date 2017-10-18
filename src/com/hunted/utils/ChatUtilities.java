package com.hunted.utils;

import com.hunted.handlers.player.PlayerHandler;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

import static org.bukkit.ChatColor.*;

public class ChatUtilities {

    public static void broadcast(String msg) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendMessage(starter() + msg);

        }

    }

    public static void broadcastServer(String msg) {

        Bukkit.broadcastMessage(serverStarter() + msg);

    }

    public static void broadcastNoStarter(String msg) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendMessage(msg);

        }

    }

    public static void playTitle(Player p, String msg, int in, int stay, int out) {

        CraftPlayer craftplayer = (CraftPlayer) p;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        PacketPlayOutTitle lengthPacket = new PacketPlayOutTitle(in, stay, out);
        IChatBaseComponent message = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, message);
        connection.sendPacket(lengthPacket);
        connection.sendPacket(titlePacket);

    }

    public static void playSubtitle(Player p, String msg, int in, int stay, int out) {

        CraftPlayer craftplayer = (CraftPlayer) p;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        PacketPlayOutTitle lengthPacket = new PacketPlayOutTitle(in, stay, out);
        IChatBaseComponent message = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, message);
        connection.sendPacket(lengthPacket);
        connection.sendPacket(titlePacket);

    }

    public static void infoBar(String msg) {

        for (Player p : Bukkit.getOnlinePlayers()) {

            CraftPlayer craftplayer = (CraftPlayer) p;
            PlayerConnection connection = craftplayer.getHandle().playerConnection;
            IChatBaseComponent warning = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
            PacketPlayOutChat packet = new PacketPlayOutChat();

            try {

                Field field = packet.getClass().getDeclaredField("a");
                field.setAccessible(true);
                field.set(packet, warning);
                field.setAccessible(!field.isAccessible());

                Field Field2 = packet.getClass().getDeclaredField("b");
                Field2.setAccessible(true);
                Field2.set(packet, (byte) 2);
                Field2.setAccessible(!Field2.isAccessible());

            } catch (Exception ex) {

                ex.printStackTrace();

            }

            connection.sendPacket(packet);

        }

    }

    public static void showList(Player p) {



    }

    public static void onePlayer(String msg, Player player) {

        player.sendMessage(starter() + msg);

    }

    public static void onePlayerServer(String msg, Player player) {

        player.sendMessage(serverStarter() + msg);

    }

    public static void chat(String msg, Player player) {

        if(player == PlayerHandler.getMurderer()){

            broadcastNoStarter(chatStarter(player) + RED + player.getName() + GRAY + " » " + WHITE + msg);

        } else {

            broadcastNoStarter(chatStarter(player) + YELLOW + player.getName() + GRAY + " » " + WHITE + msg);

        }

    }

    private static String chatStarter(Player p) {

        return "";

    }

    private static String serverStarter() {

        return GRAY + "▌" + RED + " Red" + GREEN + "Apple" + RED + "Core" + GRAY + "│ " + GOLD;

    }

    private static String starter() {

        return GRAY + "▌" + RESET + "" + RED + " HUNTED" + GRAY + "│ " + GOLD;

    }

}
