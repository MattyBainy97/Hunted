package com.hunted.listeners.entity.player;

import com.hunted.Hunted;
import com.hunted.listeners.HListener;
import com.hunted.utils.ChatUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat extends HListener {
    
    public OnChat(Hunted pl){
        
        super(pl);
        
    }
    
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent pc) {

        pc.setCancelled(true);
        ChatUtilities.chat(pc.getMessage(), pc.getPlayer());
        
    }
    
}
