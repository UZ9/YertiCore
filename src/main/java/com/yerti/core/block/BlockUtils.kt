package com.yerti.core.block

import net.minecraft.server.v1_8_R3.BlockPosition
import net.minecraft.server.v1_8_R3.World
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import java.util.*

/**
 * Various utilities for [Block]
 */
object BlockUtils {
    /**
     * Breaks a block as if it was from a player
     * @param p the target player
     * @param b the target block
     */
    fun breakBlock(p: Player?, b: Block) {
        val blockBreakEvent = BlockBreakEvent(b, p)
        Bukkit.getServer().pluginManager.callEvent(blockBreakEvent)
        if (!blockBreakEvent.isCancelled) {
            blockBreakEvent.isCancelled = true
            for (drop in b.drops) {
                b.world.dropItem(b.location, drop)
            }
            b.world.playEffect(b.location, Effect.STEP_SOUND, b.type)
            b.type = Material.AIR
        }
    }

    //https://github.com/tastybento/askyblock/blob/master/src/com/wasteofplastic/askyblock/nms/v1_8_R3/NMSHandler.java#L52-L65
    @JvmStatic
    fun setBlockSuperFast(b: Block, blockId: Int, data: Byte, applyPhysics: Boolean) {
        val w: World = (b.world as CraftWorld).handle
        val chunk = w.getChunkAt(b.x shr 4, b.z shr 4)
        val bp = BlockPosition(b.x, b.y, b.z)
        val combined: Int = blockId + (data.toInt() shl 12).toByte()
        val ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(combined)
        if (applyPhysics) {
            w.setTypeAndData(bp, ibd, 3)
        } else {
            w.setTypeAndData(bp, ibd, 2)
        }
        chunk.a(bp, ibd)
    }

    fun getBlocks(l: Location, radius: Int): List<Location> {
        val w = l.world
        val xCoord = l.x.toInt()
        val zCoord = l.z.toInt()
        val YCoord = l.y.toInt()
        val tempList: MutableList<Location> = ArrayList()
        for (x in -radius..radius) {
            for (z in -radius..radius) {
                for (y in -radius..radius) {
                    tempList.add(Location(w, (xCoord + x).toDouble(), (YCoord + y).toDouble(), (zCoord + z).toDouble()))
                }
            }
        }
        return tempList
    }
}