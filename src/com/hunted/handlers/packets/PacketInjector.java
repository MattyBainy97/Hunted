package com.hunted.handlers.packets;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PacketInjector {

    private Field EntityPlayer_playerConnection;
    private Class<?> PlayerConnection;
    private Field PlayerConnection_networkManager;

    private Class<?> NetworkManager;
    private Field channel;
    private Field m;

    public PacketInjector() {

        try {

            EntityPlayer_playerConnection = Reflection.getField(Reflection.getClass("{nms}.EntityPlayer"), "playerConnection");

            PlayerConnection = Reflection.getClass("{nms}.PlayerConnection");
            PlayerConnection_networkManager = Reflection.getField(PlayerConnection, "networkManager");

            NetworkManager = Reflection.getClass("{nms}.NetworkManager");
            channel = Reflection.getField(NetworkManager, "channel");
            m = Reflection.getField(NetworkManager, "m");

        } catch (Throwable t) {

            t.printStackTrace();

        }

    }

    public void addPlayer(Player p) {

        try {

            Channel ch = getChannel(getNetworkManager(Reflection.getNmsPlayer(p)));
            if(ch.pipeline().get("PacketInjector") == null) {

                PacketHandler h = new PacketHandler(p);
                ch.pipeline().addBefore("packet_handler", "PacketInjector", h);

            }

        } catch (Throwable t) {

            t.printStackTrace();

        }

    }

    public void removePlayer(Player p) {

        try {

            Channel ch = getChannel(getNetworkManager(Reflection.getNmsPlayer(p)));

            if(ch.pipeline().get("PacketInjector") != null) {

                ch.pipeline().remove("PacketInjector");

            }

        } catch (Throwable t) {

            t.printStackTrace();

        }

    }

    private Object getNetworkManager(Object ep) {

        return Reflection.getFieldValue(PlayerConnection_networkManager, (Object) Reflection.getFieldValue(EntityPlayer_playerConnection, ep));

    }

    private Channel getChannel(Object networkManager) {

        Channel ch = null;

        try {

            ch = Reflection.getFieldValue(channel, networkManager);

        } catch (Exception e) {

            ch = Reflection.getFieldValue(m, networkManager);

        }

        return ch;
    }
}