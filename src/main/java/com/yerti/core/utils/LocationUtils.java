package com.yerti.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    /**
     * Converts a location to a serialized string
     * Used for storing in config/database
     * Format: WorldName;;X;;Y;;Z
     * @param loc
     * @return serialized string
     */
    public static String serializeLocation(Location loc) {
        return loc.getWorld().getName() + ";;" + loc.getX() + ";;" + loc.getY() + ";;" + loc.getZ();
    }

    /**
     * Retrieves a location from a serialized string
     * A serialized string can be made from {@link LocationUtils#serializeLocation(Location)}
     * @param string
     * @return output location
     */
    public static Location deserializeLocation(String string) {
        String[] split = string.split(";;");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));

    }




}
