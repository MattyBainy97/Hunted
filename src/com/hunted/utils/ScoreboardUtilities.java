package com.hunted.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardUtilities {

    private static HashMap<UUID, Scoreboard> scoreboardList = new HashMap<>();

    public static void initialisePlayerScoreboard(Player p, Player murderer) {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "     Hunted     ");

        Team survivors = scoreboard.registerNewTeam("survivors");
        Team killer = scoreboard.registerNewTeam("killer");

        survivors.setPrefix(ChatColor.YELLOW  + "");
        survivors.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
        survivors.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);

        killer.setPrefix(ChatColor.RED + "");
        killer.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);

        Score empty = objective.getScore("");
        empty.setScore(10);
        Score roleTitle = objective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Role");
        roleTitle.setScore(9);
        if(p == murderer) {

            Score role = objective.getScore(ChatColor.RED + "KILLER");
            role.setScore(8);

        }else{

            Score role = objective.getScore(ChatColor.YELLOW + "SURVIVOR");
            role.setScore(8);

        }
        Score empty2 = objective.getScore(" ");
        empty2.setScore(7);
        Score timerTitle = objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Timer");
        timerTitle.setScore(6);
        Score timer = objective.getScore(ChatColor.WHITE + "00:00");
        timer.setScore(5);
        Score empty3 = objective.getScore("  ");
        empty3.setScore(4);
        Score genTitle = objective.getScore(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Generators");
        genTitle.setScore(3);
        Score goldVal = objective.getScore(ChatColor.WHITE + "5");
        goldVal.setScore(2);
        Score empty6 = objective.getScore("   ");
        empty6.setScore(1);

        for(Player player : Bukkit.getOnlinePlayers()) {

            if(player != murderer) {

                survivors.addEntry(player.getName());

            }else{

                killer.addEntry(player.getName());

            }

        }

        scoreboardList.put(p.getUniqueId(), scoreboard);

        p.setScoreboard(scoreboard);

    }

    public static void setTime(Player p, int newTime) {

        int currentTime = newTime - 1;

        String currentTimeString = String.format("%02d:%02d", currentTime / 60, currentTime % 60);

        String newTimeString = String.format("%02d:%02d", newTime / 60, newTime % 60);

        Scoreboard board = p.getScoreboard();
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        board.resetScores(ChatColor.WHITE + currentTimeString);
        Score timer = objective.getScore(ChatColor.WHITE + newTimeString);
        timer.setScore(5);

    }

}
