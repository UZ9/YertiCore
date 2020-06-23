package com.yerti.core.utils;


import com.yerti.core.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String serializeLocation(Location loc) {
        return loc.getWorld() + ";;" + loc.getX() + ";;" + loc.getY() + ";;" + loc.getZ();
    }

    public static void saveLocation(Config config, Location loc, String path) {
        config.set(path + ".world", loc.getWorld().getName());
        config.set(path + ".x", loc.getX());
        config.set(path + ".y", loc.getY());
        config.set(path + ".z", loc.getZ());
        config.save();
    }

    public static Location getLocation(Config config, String path) {
        if (config.getConfigurationSection(path) == null) return null;

        String world = config.getTranslation(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");
        Location newLocation = new Location(Bukkit.getWorld(world), x, y, z);
        newLocation.setYaw(yaw);
        newLocation.setPitch(pitch);
        return newLocation;
    }

    public static Location deserializeLocation(String string) {
        String[] split = string.split(";;");

        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }


}
