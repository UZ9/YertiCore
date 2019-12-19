package com.yerti.core.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
