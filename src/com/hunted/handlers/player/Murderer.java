package com.hunted.handlers.player;

import com.hunted.handlers.game.HItem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public enum Murderer {

    TRAPPER("Trapper",
              "eyJ0aW1lc3RhbXAiOjE0OTI1ODAzNzU1MDksInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJZZWxlaGEiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU4NTRjMGM1OGY3NDlhYTM3ODllNmIyNDgyNTkzNDZjYjkyNzJiMjJlZjdmNDBhY2I4OGU2MDZlNTY3OWNjIn19fQ==",
              "TGnOoN/S+m5Fd+MMY6KnGkL/r91qWk1db2PdEDgPJ4ZDSQtH2uXvbj9G0hNZdNHc3jlzuH264HHqy5cQP2z5+n7s10VP+MujGhdnLrD28wGG4+O3YQhHNGgU3+ms6pKS3nNCsL8mrtfPiSj/tY38RuqruA7dAzBDtMH9dR6zHMnxO9NMO4AJR8yQtfWvUPvxqu22DtFMpr0ys7FPAKyPhNA0Ybu27kQdyfAYtQWJQkDSsEgYgnq9i18YEnoM+7lMzkgNivLdvR5a3O2DPpBXoTBTC6XpTbRrdGI4BHtXKD1385I8vMx2QC4k4k9sJfENAbXEE3CY5Ueq1pwGTpf4jWJl7HPpWoyuKijmrPLUJ9o26HLudHBDo/nZM1jsfFBWhZLIwx+9ABClx1zBaS1IUECpMrQzMNUrho8rk4GbVZpYtYiprTaP9j+NROsXZtaPNW6tbpC5lzFXwCyu0jhId15VaHN6tutrsRRvxhln2KlKnaLbLEs9zrXWuVvEsGhMK7o4QQCPL7ZV4t5/2SsFvBaDm099F1j+XAFNUdOxWr1Hga4Wt6JV9vm9ldbvvAuTkTWdNXtIYAVsJmAvuibPhbzcmsGbYNYKzOo6ECoqOkRNt68naN6GMPPPGib0H/Ra3goYh4JtpqEo7PTCNSuXZntpjckg9NntZRmf9VCMmtE=",
              HItem.getTrapperCleaver(),
              HItem.getTrapperTrap()),
    WRAITH("Wraith",
              "eyJ0aW1lc3RhbXAiOjE0OTI1ODA0NDM1NTAsInByb2ZpbGVJZCI6IjQzYTgzNzNkNjQyOTQ1MTBhOWFhYjMwZjViM2NlYmIzIiwicHJvZmlsZU5hbWUiOiJTa3VsbENsaWVudFNraW42Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83ZTNjNDE4ZTMwOWZmMjYxYmM0ZGYxOTczNTE0ZjE3M2YzNTliMDljODEzMDc2OGMxMjE1OGJiYzFkZTg1OGEifX19",
              "flivcAJJqLZnypjlv5BBuGOrT6/fPx7r2deoAVHDdQZyFZfNo7qMcgdd6JZRtQR3q9pL4oEmmfDkwsSUqaTnAojwPnraaRPYCoh9DFLgNUgqA9yCBrX0PWfx8tPVhfUS83kohXMhqKjQ2H3pNNNgj1K5lcYYTErd5ZBZvHvTZVxW6q8BzTANg+xjY7sWNttbwu8zqeQrl6XZkULLPuZCWZ8HIWC0Ra7lywIA/E+tVCmRv2r8hDFbOsc74OQhqF87lIx3RkqfJZ+KOe9oR0YXMzmDMUsfhCOpEjatQeiWTUHepTB8dYQvfd/kSmr0aQjgWRRYVfPe8JV70B1gtUlGmDsI3aVovxN/dB5MK/v2wDqUXvRUoWuoPPtBSZra/DFPMDO2wIhZWbUy5PRRfkPv/ZNESjE/6Td5aU7f5DK9eURq4+tg9B3gUsRbDVkioKHhIPzrMi6yJjnklo7VaqsxHg7DXWJuOWDXu6OSWUiNRi2K1n7ZlBuHgWExDF6X1uEt12UReH5DnrVc5hoxMd5F6thyIGQNVHVCg/pbiXCMhCDqUM8Qkw950GR0ZKHdt2UDuJqtMGSmrTVkYHKHvOqTJthcMrNIJTHbuKcCngCaRBdq5aD9sqWRHfw6gVWj2Z7/nOJgep/1mrfniRxMd0cFeqyffHJ3cfRiJhHzgj2Nl1w=",
              HItem.getWraithBone(),
              HItem.getRealmCrosserWorld()),
    HILLBILLY("Hillbilly",
              "eyJ0aW1lc3RhbXAiOjE0OTI1ODA0OTUyNzcsInByb2ZpbGVJZCI6IjNlMjZiMDk3MWFjZDRjNmQ5MzVjNmFkYjE1YjYyMDNhIiwicHJvZmlsZU5hbWUiOiJOYWhlbGUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2MzNDdmNzljYmI3YTRkNjY0MzU2ZmE4YThjYmI3M2MzYThmNWNkOTNhODJhNTg2MjQxY2E1NjQwMjAyOGIxMTAifX19",
              "P3tyHz0ynojw+fqD7xb+AlKPQUcnsBPeHK4Fy140j7QOXeI+F7a6dZ/4JBMKHT7FGH3/iv4CUObswjSHlQF+vx7afeymtZmPNiwpfSAP9TIbQquM11KMjo0qF3EfBAJ61+gZ8wQ+sA+H13uxAhDxV2rQFgRBeJLTq1ViICXoAZT/O3AGsg4eZOIVjCJDBspxd0+umrdMqYaVPR8dDNQlj2snm6RltrQ+Jh+4y3/RkKZlrLgKo2QM09Bs3dF9UE0skMoeg8b8QS7QXSbHHJEVaZ8aLty1W+RnuQx56/6PEDyL0wAbHJQd+LKWYww3Rm4YP6V7AGo2u6pLDjvb4syC3vm2ngbsogFndHfKpkUVlZuvS5A0xdnYEZ7pBqyFSLX6I9fylADOAW+TsgMlO1EE+cPhkUHNM8Ej/gDDk7zzDBj2bbqwhpz9MNi87saUU/+J8f63GmzD8hbI1Zlm324i5Q6YYt9O1Vq+97TNvrfkG9Ow07Cb9cwvltUkbGS+fOAgiVSRInbBd5/LiQAkmIg7RpEfkWA9HLMqdj67tLi8wkJhxc7f/5p6gdaRsFGQ19dCYzYKxCn1HP+D4Bo9i+L7OuMJN9yZvP5eKQyz7+YUbRDtA6CVduie8DzDaM2RJOywDoB3k/4oZnDIRVuZvJ/6I5T9lfqJTYVev62gBIXkVLs=",
              HItem.getHillbillyHoe(),
              HItem.getHillbillyFrenzy());

    private String name;
    private String texture;
    private String signature;
    private ItemStack mainItem;
    private ItemStack ability;

    private static HashMap<UUID, Murderer> currentMurderer = new HashMap<>();

    Murderer(String name, String texture, String signature, ItemStack mainItem, ItemStack ability){

        this.name = name;
        this.texture = texture;
        this.signature = signature;
        this.mainItem = mainItem;
        this.ability = ability;

    }

    public String getName(){

        return name;

    }

    public String getTexture(){

        return texture;

    }

    public String getSignature(){

        return signature;

    }

    public ItemStack getMainItem(){

        return mainItem;

    }

    public ItemStack getAbility(){

        return ability;

    }

    public static void setCurrentMurderer(Player p, Murderer m){

        UUID uuid = p.getUniqueId();

        currentMurderer.put(uuid, m);

    }

    public static Murderer getTypeFromPlayer(Player p){

        UUID uuid = p.getUniqueId();

        return currentMurderer.get(uuid);

    }

    public static Murderer getRandomSkin() {

        Random rand = new Random();
        int choice = rand.nextInt((3 - 1) + 1) + 1;

        if (choice == 1) {

            return Murderer.TRAPPER;

        }else if(choice == 2){

            return Murderer.WRAITH;

        }else{

            return Murderer.HILLBILLY;

        }

    }

}
