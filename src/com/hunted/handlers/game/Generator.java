package com.hunted.handlers.game;

import com.hunted.utils.ChatUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private ArmorStand as;
    private double percentage;
    private Block light1;
    private Block light2;

    private static List<Generator> generators = new ArrayList();

    public Generator(Location asLoc, Block light1, Block light2){

        this.as = (ArmorStand) asLoc.getWorld().spawnEntity(asLoc, EntityType.ARMOR_STAND);
        this.as.setBasePlate(false);
        this.as.setCustomName(ChatColor.GREEN + "Totally a Generator. Not an Armor Stand.");
        this.as.setCustomNameVisible(true);
        this.as.setVisible(false);

        this.percentage = 0;

        this.light1 = light1;
        light1.setType(Material.REDSTONE_LAMP_OFF);

        this.light2 = light2;
        light2.setType(Material.REDSTONE_LAMP_OFF);

    }

    public double getPercentage(){

        return percentage;

    }

    public void setPercentage(double percentage){

        this.percentage = percentage;

    }

    public ArmorStand getArmorStand(){

        return this.as;

    }

    public Block getLight1(){

        return light1;

    }

    public Block getLight2(){

        return light2;

    }

    public static void addGenerator(Generator generator){

        generators.add(generator);

    }

    public static void removeGenerator(Generator generator){

        generators.remove(generator);

    }

    public static List<Generator> getGenerators(){

        return generators;

    }

    public static Generator getGeneratorFromArmorStand(ArmorStand as){

        Generator generator = null;

        for(Generator g : generators){

            if(g.getArmorStand().equals(as)){

                generator = g;

            }

        }

        return generator;

    }

    public void playChargingSound(){

        as.getWorld().playSound(as.getLocation(), Sound.ENTITY_LLAMA_ANGRY, 3.0F, 1.0F);

    }

    public void generatorComplete(){

        Block b = light1.getLocation().add(0,1,0).getBlock();

        Material oldType = b.getType();

        b.setType(Material.REDSTONE_BLOCK);
        light1.setType(Material.REDSTONE_LAMP_ON);

        Block b2 = light2.getLocation().add(0,1,0).getBlock();

        Material oldType2 = b2.getType();

        b2.setType(Material.REDSTONE_BLOCK);
        light2.setType(Material.REDSTONE_LAMP_ON);

        b.setType(oldType);
        b2.setType(oldType2);

        as.getWorld().playSound(as.getLocation(), Sound.BLOCK_NOTE_PLING, 3.0F, 1.5F);

        ChatUtilities.broadcast(ChatColor.GREEN + "GENERATOR ONLINE");

    }

}
