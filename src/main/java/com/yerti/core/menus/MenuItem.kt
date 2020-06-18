package com.yerti.core.menus

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

/**
 * Custom ItemStack that has its own custom event when clicked
 */
class MenuItem : ItemStack {
    /**
     * Action for when inventory click occurs
     */
    private var event: Consumer<InventoryClickEvent>?

    constructor(stack: ItemStack?) : super(stack) {
        event = null
    }

    /**
     * Creates a MenuItem off of a material, amount, and an action for the inventory click event.
     * @param stack [ItemStack] the ItemStack to be used
     * @param event the action for when inventory click occurs
     */
    constructor(stack: ItemStack?, event: Consumer<InventoryClickEvent>?) : super(stack) {
        this.event = event
    }

    /**
     * Method for when the inventory click occurs
     * @param e
     */
    fun onClick(e: InventoryClickEvent) {
        if (event == null) return
        event!!.accept(e)
    }
}