package me.elapsedkid.plugin.antibotsystem.utils;

import org.bukkit.ChatColor;

public class ChatUtility {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
