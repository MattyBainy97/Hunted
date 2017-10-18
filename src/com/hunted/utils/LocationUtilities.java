package com.hunted.utils;

import com.hunted.handlers.game.FireworkHandler;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.Random;

public class LocationUtilities {

    private static Location[] survivorSpawns = new Location[4];
    private static Location killerSpawn;

    public static void initializeSpawns(){



    }

    public static void spawnFireworks(){

        for(int i = 0; i < 10; i++){

            Random rand = new Random();
            int x = rand.nextInt((892 - 865) + 1) + 865;
            int z = rand.nextInt((-419 + 446) + 1) - 446;

            FireworkHandler.newFirework(new Location(Bukkit.getWorld("BlockChase"), x, 4, z));

        }

    }

    public static void spawnLobby(Player p){

        p.teleport(new Location(p.getWorld(), 32.5, 4, 17.5, 180,0));

    }

}
