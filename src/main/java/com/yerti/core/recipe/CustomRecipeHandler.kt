package com.yerti.core.recipe

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

class CustomRecipeHandler : Listener {
    @EventHandler
    fun onPrepareCraft(event: PrepareItemCraftEvent) {
        val inventory = event.inventory
        if (event.inventory.result == null) return
        if (CustomRecipe.Companion.getMatrixMap() == null) return
        for ((key, value) in CustomRecipe.Companion.getMatrixMap()!!.entries) {
            if (inventory.result == key) {
                for (i in 0..8) {
                    if (inventory.matrix[i].hasItemMeta()) {
                        if (!value[i]!!.hasItemMeta()) {
                            return
                        }
                    }
                    if (inventory.matrix[i].itemMeta != value.get(i)!!.itemMeta) {
                        inventory.result = null
                        return
                    }
                }
            }
        }
    }
}