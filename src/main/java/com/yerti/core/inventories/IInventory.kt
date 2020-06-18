package com.yerti.core.inventories

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

interface IInventory : InventoryHolder {
    fun onGUI(player: Player?, slot: Int, clickedItem: ItemStack?, event: InventoryClickEvent?)
}