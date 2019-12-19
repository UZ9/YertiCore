package com.yerti.core.utils;

import org.bukkit.Location;

public class ChunkUtils {

    public static boolean chunkLoaded(Location loc) {
        return loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4);

    }

}
