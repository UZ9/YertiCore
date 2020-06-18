package com.yerti.core.inventories

import com.yerti.core.menus.MenuItem
import com.yerti.core.utils.ChatUtils.translate
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Similar Inventory system to [Page] without using keys
 */
class CustomInventory : InventoryHolder {
    var inventory: Inventory
    var holder: InventoryHolder
    private var cancelEvent = false
    /**
     * Fetches the amount of slots in the inventory
     * @return
     */
    /**
     * Sets the amount of slots in the inventory
     * @param slots
     */
    var slots: Int
    /**
     * Retrieves the display name for the inventory
     * @return
     */
    /**
     * Sets the display name for the inventory
     * @param displayName
     */
    var displayName: String
    private val items: MutableMap<Int, MenuItem> = HashMap()

    /**
     * Creates a CustomInventory off of arguments
     * @param slots
     * @param displayName
     */
    constructor(slots: Int, displayName: String?) {
        this.slots = slots
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName)
        holder = this
        inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', displayName))
    }

    constructor(type: InventoryType, displayName: String?) {
        slots = type.defaultSize
        this.displayName = translate(displayName!!)
        holder = this
        inventory = Bukkit.createInventory(this, type, displayName)
    }

    /**
     * Creates the background for the inventory
     */
    fun createBackground(backgroundItem: ItemStack?) {
        for (i in 0 until slots) {
            if (inventory.contents[i] == null || inventory.contents[i].type == Material.AIR) {
                inventory.setItem(i, backgroundItem)
            }
        }
    }

    fun fill(start: Int, end: Int, stack: ItemStack?) {
        for (i in start until end) {
            inventory.setItem(i, stack)
        }
    }

    fun setItem(index: Int, stack: ItemStack?) {
        getInventory().setItem(index, stack)
    }

    fun setItem(index: Int, item: MenuItem) {
        items[index] = item
        getInventory().setItem(index, item)
    }

    fun getItem(index: Int): ItemStack {
        return getInventory().getItem(index)
    }

    /**
     * Retrieves the inventory
     * @return the inventory object
     */
    override fun getInventory(): Inventory {
        return inventory
    }

    fun addItem(vararg item: ItemStack?) {
        inventory.addItem(*item)
    }

    /**
     * Gets the items with events
     * @return
     */
    fun getItems(): Map<Int, MenuItem> {
        return items
    }

    fun cancelsEvent(): Boolean {
        return cancelEvent
    }

    fun cancel(v: Boolean): CustomInventory {
        cancelEvent = v
        return this
    }
}