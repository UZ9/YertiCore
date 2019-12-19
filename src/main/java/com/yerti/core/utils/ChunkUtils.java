package com.yerti.core.utils;

import org.bukkit.Location;

public class ChunkUtils {

    /**
     * Returns if a {@link Location} is currently chunk loaded
     * @param loc
     * @return
     */
    public static boolean chunkLoaded(Location loc) {
        return loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4);

    }

}
