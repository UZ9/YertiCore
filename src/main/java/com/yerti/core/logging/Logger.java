package com.yerti.core.logging;

import com.yerti.core.YertiPlugin;

import java.util.logging.Level;

public class Logger {

    public static void log(Level level, String text) {
        YertiPlugin.getHookedPlugin().getLogger().log(level, text);
    }

    public static void debug(Class<?> clazz, String text) {
        YertiPlugin.getHookedPlugin().getLogger().log(Level.INFO, String.format("[%s] %s", clazz.getName(), text));
    }

}
