package com.hunted.handlers.game;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HItem {

    private static ItemStack trapperCleaver = new ItemStack(Material.IRON_AXE, 1);
    private static ItemStack trapperTrap = new ItemStack(Material.STONE_PLATE, 1);

    private static ItemStack wraithBone = new ItemStack(Material.BONE, 1);
    private static ItemStack realmCrosserWorld = new ItemStack(Material.ENDER_PEARL, 1);
    private static ItemStack realmCrosserSpirit = new ItemStack(Material.EYE_OF_ENDER, 1);

    private static ItemStack hillbillyHoe = new ItemStack(Material.IRON_HOE, 1);
    private static ItemStack hillbillyFrenzy = new ItemStack(Material.REDSTONE, 1);

    public static void initializeItemMetas(){

        //TRAPPER METAS

        ItemMeta trapperCleaverMeta = trapperCleaver.getItemMeta();
        trapperCleaverMeta.setDisplayName(ChatColor.RED + "Cleaver");
        trapperCleaverMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        trapperCleaverMeta.setLore(Arrays.asList(ChatColor.GOLD + "Old, worn meat cleaver"));
        trapperCleaver.setItemMeta(trapperCleaverMeta);
        trapperCleaver.setDurability((short) 0);

        ItemMeta trapperTrapMeta = trapperTrap.getItemMeta();
        trapperTrapMeta.setDisplayName(ChatColor.RED + "Trap");
        trapperTrapMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        trapperTrapMeta.setLore(Arrays.asList(ChatColor.GOLD + "Place this trap to try", ChatColor.GOLD + "and catch survivors"));
        trapperTrap.setItemMeta(trapperTrapMeta);
        trapperTrap.setDurability((short) 0);

        //WRAITH METAS

        ItemMeta wraithBoneMeta = wraithBone.getItemMeta();
        wraithBoneMeta.setDisplayName(ChatColor.RED + "Fibia");
        wraithBoneMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        wraithBoneMeta.setLore(Arrays.asList(ChatColor.GOLD + "Bone of a previous victim,", ChatColor.GOLD + "carved into a weapon"));
        wraithBone.setItemMeta(wraithBoneMeta);
        wraithBone.setDurability((short) 0);

        ItemMeta realmCrosserWorldMeta = realmCrosserWorld.getItemMeta();
        realmCrosserWorldMeta.setDisplayName(ChatColor.RED + "Realm Crosser");
        realmCrosserWorldMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        realmCrosserWorldMeta.setLore(Arrays.asList(ChatColor.GOLD + "Right click with this item", ChatColor.GOLD + "to enter the spirit world"));
        realmCrosserWorld.setItemMeta(realmCrosserWorldMeta);
        realmCrosserWorld.setDurability((short) 0);

        ItemMeta realmCrosserSpiritMeta = realmCrosserSpirit.getItemMeta();
        realmCrosserSpiritMeta.setDisplayName(ChatColor.RED + "Realm Crosser");
        realmCrosserSpiritMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        realmCrosserSpiritMeta.setLore(Arrays.asList(ChatColor.GOLD + "Right click with this item", ChatColor.GOLD + "to leave the spirit world"));
        realmCrosserSpirit.setItemMeta(realmCrosserSpiritMeta);
        realmCrosserSpirit.setDurability((short) 0);

        //HILLBILLY METAS

        ItemMeta hillbillyHoeMeta = hillbillyHoe.getItemMeta();
        hillbillyHoeMeta.setDisplayName(ChatColor.RED + "Haunted Hoe");
        hillbillyHoeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hillbillyHoeMeta.setLore(Arrays.asList(ChatColor.GOLD + "Old, freshly sharpened hoe."));
        hillbillyHoe.setItemMeta(hillbillyHoeMeta);
        hillbillyHoe.setDurability((short) 0);

        ItemMeta hillbillyFrenzyMeta = hillbillyFrenzy.getItemMeta();
        hillbillyFrenzyMeta.setDisplayName(ChatColor.RED + "FRENZY!");
        hillbillyFrenzyMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hillbillyFrenzyMeta.setLore(Arrays.asList(ChatColor.GOLD + "Right click with this item", ChatColor.GOLD + "to become enraged"));
        hillbillyFrenzy.setItemMeta(hillbillyFrenzyMeta);
        hillbillyFrenzy.setDurability((short) 0);


    }

    public static ItemStack getTrapperCleaver(){

        return trapperCleaver;

    }

    public static ItemStack getTrapperTrap(){

        return trapperTrap;

    }

    public static ItemStack getWraithBone() {

        return wraithBone;

    }

    public static ItemStack getRealmCrosserWorld() {

        return realmCrosserWorld;

    }

    public static ItemStack getRealmCrosserSpirit(){

        return realmCrosserSpirit;

    }

    public static ItemStack getHillbillyHoe(){

        return hillbillyHoe;

    }

    public static ItemStack getHillbillyFrenzy(){

        return hillbillyFrenzy;

    }


}
