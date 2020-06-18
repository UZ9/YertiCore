package com.yerti.core.menus

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import java.util.*
import java.util.function.Consumer

/**
 * Listener for all of the menu items
 */
class MenuListener(private val menus: List<Page>) : Listener {
    private val inventories: MutableList<Inventory?>
    /**
     * Checks for any specific [MenuItem]s and their designated events
     * @param event
     */
    @EventHandler
    fun onPlayerClick(event: InventoryClickEvent) {
        if (event.inventory == null) return
        if (!inventories.contains(event.inventory)) return
        if (event.currentItem == null || event.currentItem.type == Material.AIR) return
        event.isCancelled = true
        if (event.currentItem.itemMeta == null) return
        val menu = menus[inventories.indexOf(event.inventory)]
        menu.menuItems.forEach(Consumer { menuItem: MenuItem? ->
            if (event.currentItem.itemMeta == menuItem!!.itemMeta) {
                if (event.currentItem.type == Material.AIR)
                menuItem.onClick(event)
            }
        })
    }

    /**
     * Creates a MenuListener off of a list of [Page]s
     * @param menus
     */
    init {
        inventories = ArrayList()
        menus.forEach(Consumer { menu: Page -> inventories.add(menu.build()) })
    }
}