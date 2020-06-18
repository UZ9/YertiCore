package com.yerti.core.utils

import org.bukkit.ChatColor

object ChatUtils {
    private var prefix = ""
    fun setPrefix(prefix: String) {
        ChatUtils.prefix = prefix
    }

    @JvmStatic
    fun translate(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', "$prefix $message")
    }

    fun translateWithoutPrefix(message: String?): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }
}