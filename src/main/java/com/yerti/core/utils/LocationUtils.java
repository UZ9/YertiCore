package com.yerti.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String serializeLocation(Location loc) {
        return loc.getWorld() + ";;" + loc.getX() + ";;" + loc.getY() + ";;" + loc.getZ();
    }

    public static Location deserializeLocation(String string) {
        String[] split = string.split(";;");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));

    }




}
