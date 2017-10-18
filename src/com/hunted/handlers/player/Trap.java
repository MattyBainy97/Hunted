package com.hunted.handlers.player;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Trap {

    private static Block block;
    private static Player trapped;


    public static void setTrap(Block b){

        block = b;

    }

    public static void removeTrap(){

        block = null;

    }

    public static Block getBlock(){

        return block;

    }

    public static boolean isSet(){

        return block == null;

    }

    public static Player getTrapped() {

        return trapped;

    }

    public static void setTrapped(Player trapped) {

        Trap.trapped = trapped;

    }

    public static void removeTrapped(){

        trapped = null;

    }

}
