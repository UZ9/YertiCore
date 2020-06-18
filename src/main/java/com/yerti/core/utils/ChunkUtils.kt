package com.yerti.core.utils

import org.bukkit.Location

object ChunkUtils {
    fun chunkLoaded(loc: Location): Boolean {
        return loc.world.isChunkLoaded(loc.blockX shr 4, loc.blockZ shr 4)
    }
}