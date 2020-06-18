package com.yerti.core.inventories

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * Listener used for IInventory menus
 */
class InventoryHandler : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is IInventory) { //Inventory is one of ours
            event.isCancelled = true
            val inventory = event.inventory.holder as IInventory
            inventory.onGUI(event.whoClicked as Player, event.rawSlot, event.currentItem, event)
        }
        if (event.whoClicked.openInventory.topInventory != null && event.whoClicked.openInventory.topInventory.holder is CustomInventory) {
            if (event.isShiftClick) {
                event.isCancelled = true
            }
        }
        if (event.inventory.holder is CustomInventory) {
            val inventory = event.inventory.holder as CustomInventory
            if (inventory.cancelsEvent() && event.rawSlot < inventory.slots) event.isCancelled = true
            println(inventory.getItems().size)
            if (inventory.getItems().containsKey(event.slot)) {
                inventory.getItems().getValue(event.slot).onClick(event)
            }
        }
    }
}