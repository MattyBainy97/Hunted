package com.hunted.handlers.game;

import com.hunted.Hunted;
import com.hunted.handlers.player.PlayerHandler;
import com.hunted.utils.ChatUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Hook {

    private Location hangLoc;
    private Block triggerBlock;
    private Player hanging;
    private boolean taken;
    private HangingTimer hangingTimer;

    private static HashMap<UUID, Integer> timesHooked = new HashMap<>();

    private static List<Hook> hooks = new ArrayList<>();

    public Hook(Location hangLoc, Block triggerBlock){

        this.hangLoc = hangLoc;
        this.triggerBlock = triggerBlock;
        this.hangingTimer = new HangingTimer();

    }

    public void setHanging(Player p){

        this.hanging = p;
        PlayerHandler.removeIncapacitated(p);
        PlayerHandler.addHanging(p);
        p.teleport(hangLoc);
        p.setAllowFlight(true);
        p.setFlying(true);
        p.removePotionEffect(PotionEffectType.SLOW);

        if(timesHooked.containsKey(p.getUniqueId())){

            if(timesHooked.get(p.getUniqueId()) < 2) {

                timesHooked.put(p.getUniqueId(), timesHooked.get(p.getUniqueId()) + 1);
                ChatUtilities.playTitle(p, ChatColor.DARK_RED + "HOOKED", 10, 40, 10);
                ChatUtilities.playSubtitle(p, ChatColor.WHITE + "Get hooked again and you will die", 10, 40, 10);

            } else {

                timesHooked.put(p.getUniqueId(), timesHooked.get(p.getUniqueId()) + 1);
                ChatUtilities.playTitle(p, ChatColor.DARK_RED + "SACRIFICED", 10, 40, 10);
                ChatUtilities.playSubtitle(p, ChatColor.WHITE + "You were sacrificed to the Entity", 10, 40, 10);

            }

        }else{

            timesHooked.put(p.getUniqueId(), 1);
            ChatUtilities.playTitle(p, ChatColor.DARK_RED + "HOOKED", 10, 40,10);
            ChatUtilities.playSubtitle(p, ChatColor.WHITE + "Call a survivor to come save you", 10, 40, 10);

        }

        this.taken = true;

    }

    public Player getHanging(){

        return this.hanging;

    }

    public HangingTimer getHangingTimer(){

        return this.hangingTimer;

    }

    public void removeHanging(){

        PlayerHandler.setOnCooldown(this.hanging);
        this.hanging.teleport(triggerBlock.getLocation().add(0,1,0));
        this.hanging.setFlying(false);
        this.hanging.setAllowFlight(false);
        this.hanging = null;
        this.hangingTimer.stopTimer();
        this.taken = false;

    }

    public static int getTimesHooked(Player p){

        UUID uuid = p.getUniqueId();

        if(timesHooked.containsKey(uuid))
        {

            return timesHooked.get(uuid);

        }

        return 0;
    }

    public boolean isTaken(){

        return this.taken;

    }

    public Block getTriggerBlock(){

        return this.triggerBlock;

    }

    public static void addHook(Hook hook){

        hooks.add(hook);

    }

    public static void removeHook(Hook hook){

        hooks.remove(hook);

    }

    public static List<Hook> getHooks(){

        return hooks;

    }

    public static Hook getHookFromTrigger(Block b){

        Hook hook = null;

        for(Hook h : hooks){

            if(h.getTriggerBlock().equals(b)){

                hook = h;

            }

        }

        return hook;

    }

    public static Hook getHookFromPlayer(Player p){

        Hook hook = null;

        for(Hook h : hooks){

            if(h.getHanging() != null) {

                if (h.getHanging().equals(p)) {

                    hook = h;

                }

            }

        }

        return hook;

    }

    public class HangingTimer{

        private int task;

        public HangingTimer(){}

        public void startTimer(){

            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Hunted.getPlugin(), new Runnable() {

                @Override
                public void run() {

                    if(PlayerHandler.hasBossBar(hanging)) {

                        if (PlayerHandler.getCurrentBossHealth(hanging) > 0) {

                            PlayerHandler.updateBossBar(hanging, (float) (PlayerHandler.getCurrentBossHealth(hanging) - 0.001));

                        } else {

                            hanging.setHealth(0);
                            hanging.getWorld().strikeLightningEffect(hangLoc);
                            removeHanging();
                            stopTimer();

                        }

                    }

                }

            },0L, 1L);


        }

        public void stopTimer(){

            Bukkit.getScheduler().cancelTask(task);

        }

    }

}
