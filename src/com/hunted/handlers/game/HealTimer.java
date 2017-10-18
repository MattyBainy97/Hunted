package com.hunted.handlers.game;

import com.hunted.utils.ChatUtilities;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class HealTimer {

    private int time;

    private static HashMap<UUID, HealTimer> healTimers = new HashMap<>();

    public HealTimer(Player player){

        healTimers.put(player.getUniqueId(), this);

    }

    public int getTime() {

        return time;

    }

    public void setTime(int time) {

        this.time = time;

    }

    public static boolean hasHealTimer(Player p){

        return healTimers.containsKey(p.getUniqueId());

    }

    public static HealTimer getHealTimerFromPlayer(Player p){

        HealTimer h = null;

        if(healTimers.containsKey(p.getUniqueId())){

            return healTimers.get(p.getUniqueId());

        }

        return h;

    }

}
