package com.yerti.core.physics

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

object EntityPhysics {
    /**
     * Launches a block with a custom velocity
     * @param blockLoc
     * @param velocity
     */
    fun launchBlock(blockLoc: Location, velocity: Vector?) {
        val block = blockLoc.block
        val material = block.type
        val data = block.data
        block.type = Material.AIR
        val fallingBlock = blockLoc.world.spawnFallingBlock(blockLoc, material, data)
        fallingBlock.dropItem = false
        fallingBlock.velocity = velocity
    }
}