package com.yerti.core.menus

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Page with key and pattern building
 */
class Page(displayName: String, size: Size) {
    private val size: Size
    private val displayName: String
    private val rowPatterns: MutableMap<Row, String>
    private val itemKeys: MutableMap<Char, MenuItem>
    /**
     * Sets the row pattern for a specific row
     * @param row for the pattern
     * @param pattern for the specific row
     * @return
     */
    fun setRowPattern(row: Row, pattern: String): Page {
        rowPatterns[row] = pattern
        return this
    }

    /**
     * Sets the key for a specific character in the pattern
     * @param character to set the MenuItem to
     * @param stack the [MenuItem] for the character
     * @return the page for further modification
     */
    fun setKey(character: Char, stack: MenuItem): Page {
        itemKeys[character] = stack
        return this
    }

    /**
     * Builds the inventory off of the patterns and keys
     * If a key is not defined for a particular character, the slot will be set to air
     * @return the result inventory
     */
    fun build(): Inventory {
        val inventory = Bukkit.createInventory(null, size.slotAmount, displayName)
        for (row in rowPatterns.keys) {
            if (rowPatterns[row]!!.toCharArray().size > 9) {
                throw IndexOutOfBoundsException("Character amount was large than 9 using setRowPattern")
            }
            var slot = 0
            for (character in rowPatterns[row]!!.toCharArray()) {
                if (itemKeys[character] == null || character == ' ') {
                    inventory.setItem(slot, ItemStack(Material.AIR))
                } else {
                    inventory.setItem(slot, itemKeys[character])
                }
                slot++
            }
        }
        return inventory
    }
    //TODO: Update this to be more efficient and compact
    /**
     * Retrieves all of the menu items on the page
     * @return a list of [MenuItem]s
     */
    val menuItems: List<MenuItem?>
        get() = Arrays.asList(*itemKeys.values.toTypedArray())

    /**
     * Creates a page with a displayname and size
     * @param displayName
     * @param size
     * For size, [Size]
     */
    init {
        rowPatterns = HashMap()
        itemKeys = HashMap()
        this.size = size
        this.displayName = displayName
    }
}