package com.yerti.core.utils;


import com.yerti.core.YertiPlugin;

public class NMSUtils {
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + YertiPlugin.getHookedPlugin().getServer().getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
