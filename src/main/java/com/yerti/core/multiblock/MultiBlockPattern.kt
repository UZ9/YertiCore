package com.yerti.core.multiblock

import org.bukkit.Material
import java.util.*

class MultiBlockPattern //x
//x
//x
//[0][0] 3@3
//[0][1] 333
//[0][2] 333
(private val pattern: Array<Array<String>>) {
    private val key: MutableMap<Char, Material> = HashMap()
    fun addKey(key: Char, type: Material): MultiBlockPattern {
        this.key[key] = type
        return this
    }

    fun build(): Array<Array<Array<Material?>>> {
        val built = Array(pattern.size) { Array(pattern[0].size) { arrayOfNulls<Material>(pattern[0].size) } }
        for (row in pattern.indices) {
            for (col in pattern[row].indices) {
                for (c in pattern[col][row].indices) {
                    val character = pattern[col][row].toCharArray()[c]
                    built[row][col][c] = key.getOrDefault(character, Material.AIR)
                }
            }
        }
        return built
    }

}