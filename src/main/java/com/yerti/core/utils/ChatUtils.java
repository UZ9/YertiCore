package com.yerti.core.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    private static String prefix = "";

    public static void setPrefix(String prefix) {
        ChatUtils.prefix = prefix;
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', prefix + " " + message);
    }

    public static String translateWithoutPrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&',  message);
    }

}
