package com.yerti.core.multiblock

import org.bukkit.Location
import org.bukkit.Material

object MultiBlockUtils {
    //Find outer box: ceil(size / 2)
    fun found(loc: Location, xSize: Int, ySize: Int, zSize: Int, pattern: MultiBlockPattern): Boolean {
        var loc = loc
        loc = loc.subtract(Math.ceil(xSize / 2.0) - 1, Math.ceil(ySize / 2.0) - 1, Math.ceil(zSize / 2.0) - 1)
        val matrix = pattern.build()
        for (x in 0 until xSize) {
            for (y in 0 until ySize) {
                for (z in 0 until zSize) { //new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock().setType(matrix[x][y][z]);
                    if (matrix!![x]!![y]!![z] == Material.AIR) continue
                    if (loc.clone().add(x.toDouble(), y.toDouble(), z.toDouble()).block.type != matrix[x]!![y]!![z]) {
                        return false
                    }
                }
            }
        }
        return true
    }
}