package com.hunted.handlers.server;

import com.hunted.Hunted;
import com.hunted.handlers.game.Generator;
import com.hunted.handlers.game.Hook;
import com.hunted.handlers.player.*;
import com.hunted.utils.ChatUtilities;
import com.hunted.utils.ScoreboardUtilities;
import net.minecraft.server.v1_11_R1.*;
import net.minecraft.server.v1_11_R1.WorldBorder;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;

public class HCommands implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            if (commandLabel.equalsIgnoreCase("name")) {

                if(args.length == 0){

                    ChatUtilities.onePlayer("Please enter a name", player);

                }else if(args.length >= 2){

                    ChatUtilities.onePlayer("Too many arguments supplied", player);

                }else{

                    if(args[0].length() > 16 || args[0].length() < 3){

                        ChatUtilities.onePlayer("Player name must be between 3 and 16 characters", player);

                    }else if(args[0].equalsIgnoreCase("reset")) {

                        Player found = null;

                        for(Player p : Bukkit.getOnlinePlayers()){

                            if(p.getName().equalsIgnoreCase(player.getDisplayName()) && !p.getUniqueId().equals(player.getUniqueId())){

                                found = p;

                            }

                        }

                        if(found != null){

                            ChatUtilities.onePlayer(ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.GOLD + " wants their name back", found);
                            PlayerHandler.setPlayerName(found, found.getDisplayName());
                            ChatUtilities.onePlayer("Player name reverted to " + ChatColor.DARK_AQUA + found.getDisplayName(), found);

                        }

                        PlayerHandler.setPlayerName(player, player.getDisplayName());
                        ChatUtilities.onePlayer("Player name reverted to " + ChatColor.DARK_AQUA + player.getDisplayName(), player);

                    }else{

                        boolean found = false;

                        for(Player p : Bukkit.getOnlinePlayers()){

                            if(p.getName().equalsIgnoreCase(args[0])){

                                found = true;

                            }

                        }

                        if(found == false) {

                            PlayerHandler.setPlayerName(player, args[0]);
                            ChatUtilities.onePlayer("Player name changed to " + ChatColor.DARK_AQUA + args[0], player);

                        }else{

                            ChatUtilities.onePlayer("This name is already taken by another player", player);

                        }

                    }

                }

            }

            if (commandLabel.equalsIgnoreCase("skin")){

                if(args.length == 0){

                    ChatUtilities.onePlayer("Please enter a skin name", player);

                }else if(args.length >= 2){

                    ChatUtilities.onePlayer("Too many arguments supplied", player);

                }else{

                    if(args[0].equalsIgnoreCase("trapper")){

                        PlayerHandler.setPlayerSkin(player, Murderer.TRAPPER);
                        ChatUtilities.onePlayer("Murderer changed to " + ChatColor.DARK_AQUA + Murderer.TRAPPER.getName(), player);

                    }else if(args[0].equalsIgnoreCase("wraith")){

                        PlayerHandler.setPlayerSkin(player, Murderer.WRAITH);
                        ChatUtilities.onePlayer("Murderer changed to " + ChatColor.DARK_AQUA + Murderer.WRAITH.getName(), player);

                    }else if(args[0].equalsIgnoreCase("hillbilly")){

                        PlayerHandler.setPlayerSkin(player, Murderer.HILLBILLY);
                        ChatUtilities.onePlayer("Murderer changed to " + ChatColor.DARK_AQUA + Murderer.HILLBILLY.getName(), player);

                    }else{

                        ChatUtilities.onePlayer("Please enter an valid skin", player);

                    }

                }

            }

            if (commandLabel.equalsIgnoreCase("fake")){

                EntityPlayer ep = ((CraftPlayer) player).getHandle();

                FakePlayer fp = new FakePlayer(ep);
                fp.teleport(player.getLocation());

            }

            if(commandLabel.equalsIgnoreCase("boss")){

                PlayerHandler.sendBossBar(player);

            }

            if(commandLabel.equalsIgnoreCase("nojump")){

                for(Player p : Bukkit.getOnlinePlayers()){

                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000, 128, false, false));

                }

            }

            if(commandLabel.equalsIgnoreCase("cleareffects")){

                for(Player p : Bukkit.getOnlinePlayers()){

                    p.removePotionEffect(PotionEffectType.JUMP);
                    p.removePotionEffect(PotionEffectType.SLOW);

                    p.setHealth(20.0);

                    if(p.getGameMode() != GameMode.CREATIVE) {

                        p.setFlying(false);
                        p.setAllowFlight(false);

                    }

                    for(Entity e : p.getPassengers()){

                        p.removePassenger(e);
                        e.remove();

                    }

                    for(Entity e : player.getWorld().getEntities()){

                        if(e instanceof ArmorStand){

                            e.remove();

                        }

                    }

                    WorldBorder border = ((CraftPlayer)p).getHandle().getWorld().getWorldBorder();

                    PacketPlayOutWorldBorder borderPacket = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

                    try {

                        Field field = borderPacket.getClass().getDeclaredField("i");
                        field.setAccessible(true);
                        field.setInt(borderPacket, 1);
                        field.setAccessible(!field.isAccessible());

                    } catch (Exception ex) {

                        ex.printStackTrace();

                    }

                    if(PlayerHandler.isHanging(p)) {

                        PlayerHandler.removeHanging(p);

                    }

                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(borderPacket);

                    for(Player pla : Bukkit.getOnlinePlayers()) {

                        PlayerHandler.playerMeta(p, (byte) 0, pla);

                    }

                }

            }

            if(commandLabel.equalsIgnoreCase("spiritsound")){

                SpiritWorld.playSound(player.getLocation());

            }

            if(commandLabel.equalsIgnoreCase("setboard")) {

                World world = Bukkit.getWorlds().get(0);

                //FARMHOUSE
                Hook.addHook(new Hook(new Location(world, -10.5, 14.2, 3.5, 180, 18), world.getBlockAt(-11, 12, 2)));
                Hook.addHook(new Hook(new Location(world, -8.5, 14.2, 5.5, -90, 18), world.getBlockAt(-8, 12, 5)));
                Hook.addHook(new Hook(new Location(world, -10.5, 14.2, 7.5, 0, 18), world.getBlockAt(-11, 12, 8)));
                Hook.addHook(new Hook(new Location(world, -12.5, 14.2, 5.5, 90, 18), world.getBlockAt(-14, 12, 5)));

                Generator.addGenerator(new Generator(new Location(world, -18.5, 31, 20.5), world.getBlockAt(-17, 34, 19), world.getBlockAt(-17, 34, 21)));

                //GRAVEYARD
                Hook.addHook(new Hook(new Location(world, -8.5, 25.2, 54.5, 0, 18), world.getBlockAt(-9, 23, 55)));

                Generator.addGenerator(new Generator(new Location(world, -27.5, 24, 74.5), world.getBlockAt(-27, 29, 76), world.getBlockAt(-29, 29, 76)));

                //SLAUGHTERHOUSE
                Hook.addHook(new Hook(new Location(world, 58.5, 26.2, 70.5, -90, 18), world.getBlockAt(59, 24, 70)));

                Generator.addGenerator(new Generator(new Location(world, 72.5, 24, 47.5), world.getBlockAt(73, 29, 49), world.getBlockAt(71, 29, 49)));

                //BARN
                Hook.addHook(new Hook(new Location(world, 41.5, 25.2, -23.5, -90, 18), world.getBlockAt(41, 23, -23)));

                //MAZE
                Generator.addGenerator(new Generator(new Location(world, 29.5, 24, 24.5), world.getBlockAt(31, 32, 23), world.getBlockAt(31, 32, 25)));

                //WOODS
                Hook.addHook(new Hook(new Location(world, 51.5, 25.2, 25.5, 0, 18), world.getBlockAt(51, 23, 26)));
                Hook.addHook(new Hook(new Location(world, 18.5, 25.2, 10.5, 180, 18), world.getBlockAt(18, 23, 9)));
                Hook.addHook(new Hook(new Location(world, 64.5, 25.2, 1.5, 90, 18), world.getBlockAt(63,23,1)));

                Generator.addGenerator(new Generator(new Location(world, 12.5, 24, -18.5), world.getBlockAt(14, 28, -20), world.getBlockAt(14, 28, -18)));
                Generator.addGenerator(new Generator(new Location(world, 76.5, 24, 1.5), world.getBlockAt(75, 28, -1), world.getBlockAt(77, 28, -1)));

                //WALLS
                Generator.addGenerator(new Generator(new Location(world, 20.5,24, 44.5), world.getBlockAt(18,30,45), world.getBlockAt(18,30,43)));

                for (Player p : Bukkit.getOnlinePlayers()) {

                    PlayerHandler.addOnline(p);

                }

                Player murderer = PlayerHandler.chooseMurderer();

                for (Player p : Bukkit.getOnlinePlayers()) {

                    Hunted.getInjector().addPlayer(p);

                    if(p != murderer){

                        PlayerHandler.addSurvivor(p);

                    }else{

                        Murderer m = Murderer.WRAITH;

                        PlayerHandler.setPlayerSkin(p, m);
                        PlayerHandler.setPlayerName(p, m.getName());

                        p.getInventory().setItem(0, m.getMainItem());
                        p.getInventory().setItem(1, m.getAbility());

                        Murderer.setCurrentMurderer(p, m);

                    }

                    Bukkit.getScheduler().scheduleSyncRepeatingTask(Hunted.getPlugin(), new Runnable(){

                        @Override
                        public void run(){

                            if(p != murderer) {

                                EntityPlayer ep = ((CraftPlayer) p).getHandle();
                                WorldBorder border = ep.getWorld().getWorldBorder();

                                double distance = p.getLocation().distance(murderer.getLocation());

                                if(!SpiritWorld.isInSpiritWorld()) {

                                    if (distance <= 20 && distance >= 10) {

                                        if (!WarningSound.isType(p, WarningSound.Type.FAR) && WarningSound.isPlaying(p)) {

                                            WarningSound.stopSound(p);

                                        } else {

                                            if (!WarningSound.isPlaying(p)) {

                                                WarningSound.playSound(p, 4, 30);

                                            }

                                        }

                                        PacketPlayOutWorldBorder borderPacket = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

                                        try {

                                            Field field = borderPacket.getClass().getDeclaredField("i");
                                            field.setAccessible(true);
                                            field.setInt(borderPacket, 1);
                                            field.setAccessible(!field.isAccessible());

                                        } catch (Exception ex) {

                                            ex.printStackTrace();

                                        }

                                        ep.playerConnection.sendPacket(borderPacket);

                                    } else if (distance < 10 && distance >= 5) {

                                        if (!WarningSound.isType(p, WarningSound.Type.MEDIUM) && WarningSound.isPlaying(p)) {

                                            WarningSound.stopSound(p);

                                        } else {

                                            if (!WarningSound.isPlaying(p)) {

                                                WarningSound.playSound(p, 4, 15);

                                            }

                                        }

                                        PacketPlayOutWorldBorder borderPacket = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

                                        try {

                                            Field field = borderPacket.getClass().getDeclaredField("i");
                                            field.setAccessible(true);
                                            field.setInt(borderPacket, 1);
                                            field.setAccessible(!field.isAccessible());

                                        } catch (Exception ex) {

                                            ex.printStackTrace();

                                        }

                                        ep.playerConnection.sendPacket(borderPacket);

                                    } else if (distance < 5) {

                                        if (!WarningSound.isType(p, WarningSound.Type.CLOSE) && WarningSound.isPlaying(p)) {

                                            WarningSound.stopSound(p);

                                        } else {

                                            if (!WarningSound.isPlaying(p)) {

                                                WarningSound.playSound(p, 4, 10);

                                            }

                                        }

                                        PacketPlayOutWorldBorder borderPacket = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

                                        try {

                                            Field field = borderPacket.getClass().getDeclaredField("i");
                                            field.setAccessible(true);
                                            field.setInt(borderPacket, 10000);
                                            field.setAccessible(!field.isAccessible());

                                        } catch (Exception ex) {

                                            ex.printStackTrace();

                                        }

                                        ep.playerConnection.sendPacket(borderPacket);

                                    } else {

                                        if (WarningSound.isPlaying(p)) {

                                            WarningSound.stopSound(p);

                                        }

                                        PacketPlayOutWorldBorder borderPacket = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);

                                        try {

                                            Field field = borderPacket.getClass().getDeclaredField("i");
                                            field.setAccessible(true);
                                            field.setInt(borderPacket, 1);
                                            field.setAccessible(!field.isAccessible());

                                        } catch (Exception ex) {

                                            ex.printStackTrace();

                                        }

                                        ep.playerConnection.sendPacket(borderPacket);

                                    }

                                }else{

                                    if (WarningSound.isPlaying(p)) {

                                        WarningSound.stopSound(p);

                                    }

                                }

                            }

                        }

                    }, 0L, 10L);

                }

                for(Player p : Bukkit.getOnlinePlayers()){

                    ScoreboardUtilities.initialisePlayerScoreboard(p, murderer);

                }

            }

        }

        return false;

    }

}
