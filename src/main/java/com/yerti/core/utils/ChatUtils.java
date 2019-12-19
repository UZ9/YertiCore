package com.yerti.core.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    /**
     * Translates a message as an easier way of doing {@link ChatColor#translateAlternateColorCodes(char, String)}
     * @param message message parameter
     * @return formatted message
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
