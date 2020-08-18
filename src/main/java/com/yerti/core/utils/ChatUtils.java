package com.yerti.core.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    private static String prefix = ""; // Stores the prefix for {@link #stylePrefix(String)}

    /**
     * Sets the prefix for {@link #stylePrefix(String)}
     * @param prefix
     */
    public static void setPrefix(String prefix) {
        ChatUtils.prefix = prefix;
    }

    /**
     * Adds chat extensions.color to a message. Will use prefix if set correctly.
     * @param message The message to be styled
     * @return The styled message
     */
    public static String stylePrefix(String message) {
        return ChatColor.translateAlternateColorCodes('&', prefix.equals("") ? "" : prefix + " " + message);
    }

    /**
     * Adds chat extensions.color to a message automatically (to remove unnecessary code clutter)
     * @param message The message to be styled
     * @return The styled message
     */
    public static String style(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
