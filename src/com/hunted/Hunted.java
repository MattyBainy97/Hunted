package com.hunted;

import com.hunted.handlers.game.HItem;
import com.hunted.handlers.packets.PacketInjector;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.handlers.server.HCommands;
import com.hunted.listeners.entity.EntityDamage;
import com.hunted.listeners.entity.EntityDamageByEntity;
import com.hunted.listeners.entity.EntityRegen;
import com.hunted.listeners.entity.player.*;
import com.hunted.listeners.server.ListPing;
import com.hunted.listeners.world.*;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Hunted extends JavaPlugin {

    private static Plugin plugin;

    private static PacketInjector injector;

    @Override
    public void onEnable() {

        injector = new PacketInjector();

        HItem.initializeItemMetas();
        registerListeners();

        HCommands hc = new HCommands();
        getCommand("name").setExecutor(hc);
        getCommand("skin").setExecutor(hc);
        getCommand("fake").setExecutor(hc);
        getCommand("nojump").setExecutor(hc);
        getCommand("cleareffects").setExecutor(hc);
        getCommand("spiritsound").setExecutor(hc);
        getCommand("setboard").setExecutor(hc);
        getCommand("boss").setExecutor(hc);

        plugin = this;

    }

    @Override
    public void onDisable() {

        plugin = null;

        PlayerHandler.reset();

        Bukkit.getScheduler().cancelAllTasks();

    }

    public void registerListeners() {

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractEntity(this), this);
        pm.registerEvents(new PlayerShift(this), this);
        pm.registerEvents(new PlayerSprint(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new RedstoneActivate(this), this);
        pm.registerEvents(new OnChat(this), this);
        pm.registerEvents(new EntityDamageByEntity(this), this);
        pm.registerEvents(new EntityDamage(this), this);
        pm.registerEvents(new PlayerMove(this), this);
        pm.registerEvents(new PlayerDrop(this), this);
        pm.registerEvents(new BlockBreak(this), this);
        pm.registerEvents(new BlockPlace(this), this);
        pm.registerEvents(new PlayerSwitchHand(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerLeave(this), this);
        pm.registerEvents(new EntityRegen(this), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new ListPing(this), this);
        pm.registerEvents(new BlockGrow(this), this);
        pm.registerEvents(new AsyncPlayerPreLogin(this), this);
        pm.registerEvents(new BlockSpread(this), this);
        pm.registerEvents(new LeavesDecay(this), this);

    }


    public static Plugin getPlugin(){

        return plugin;

    }

    public static PacketInjector getInjector(){

        return injector;

    }

}
