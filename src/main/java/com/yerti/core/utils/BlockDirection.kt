package com.yerti.core.utils

import org.bukkit.Location
import org.bukkit.block.BlockFace

enum class BlockDirection(val blockFace: BlockFace) {
    EAST(BlockFace.EAST), WEST(BlockFace.WEST), NORTH(BlockFace.SOUTH), SOUTH(BlockFace.NORTH);

    fun offsetLocation(location: Location, offsetBlocks: Int): Location {
        var output = location.clone()
        output = when (this) {
            EAST -> output.add(offsetBlocks.toDouble(), 0.0, 0.0)
            WEST -> output.add(-offsetBlocks.toDouble(), 0.0, 0.0)
            NORTH -> output.add(0.0, 0.0, offsetBlocks.toDouble())
            SOUTH -> output.add(0.0, 0.0, -offsetBlocks.toDouble())
        }
        return output
    }

}