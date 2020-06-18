package com.yerti.core.utils

import com.yerti.core.items.CustomItemStack
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InventoryUtils {
    /**
     * Checks if the player can fit the ItemStack within their inventory.
     * @param player
     * @param itemStack
     * @return
     */
    fun hasInventorySpace(player: Player, itemStack: ItemStack): Boolean {
        var freeSpace = 0
        for (i in 0..35) {
            val currentItemStack = player.inventory.getItem(i)
            //Itemstack is Material.AIR
            if (currentItemStack == null) {
                freeSpace += itemStack.maxStackSize
                //Item has same item meta
            } else if (currentItemStack.isSimilar(itemStack)) {
                freeSpace += itemStack.maxStackSize - currentItemStack.amount
            }
        }
        return freeSpace >= itemStack.amount
    }

    /**
     * Checks if the inventory can fit the ItemStack within their inventory.
     * @param inventory
     * @param itemStack
     * @return
     */
    fun hasInventorySpace(inventory: Inventory, itemStack: ItemStack): Boolean {
        var freeSpace = 0
        for (i in 0..8) {
            val currentItemStack = inventory.getItem(i)
            //Itemstack is Material.AIR
            if (currentItemStack == null) {
                freeSpace += itemStack.maxStackSize
                //Item has same item meta
            } else if (currentItemStack.isSimilar(itemStack)) {
                freeSpace += itemStack.maxStackSize - currentItemStack.amount
            }
        }
        return freeSpace >= itemStack.amount
    }

    /**
     * Checks if the player have an amount of slots open
     * @param player
     * @param slots
     * @return
     */
    fun hasOpenSlots(player: Player, slots: Int): Boolean {
        var freeSlots = 0
        for (i in 0..35) {
            val currentItemStack = player.inventory.getItem(i)
            if (currentItemStack == null) {
                freeSlots++
            }
        }
        return freeSlots >= slots
    }

    /**
     * Returns the amount of a material in a given inventory
     * @param inventory
     * @param material
     * @return
     */
    fun getItemCount(inventory: Inventory, material: Material?): Int {
        var amount = 0
        for (stack in inventory.all(material).values) {
            amount += stack.amount
        }
        return amount
    }

    /**
     * Returns the amount of an itemstack in a given inventory
     * @param inventory
     * @param item
     * @return
     */
    fun getItemCount(inventory: Inventory, item: ItemStack?): Int {
        var amount = 0
        for (stack in inventory.contents) {
            if (stack == null || stack.type == Material.AIR) continue
            if (CustomItemStack(stack).isSimilar(item)) {
                amount += stack.amount
            }
        }
        return amount
    }

    /**
     * Removes an amount of a material from an inventory
     * @param inventory
     * @param stack
     * @param amount
     * @return
     */
    fun removeItems(inventory: Inventory?, stack: ItemStack?, amount: Int): Int {
        var amount = amount
        if (stack == null || inventory == null || stack.type == Material.AIR) return -1
        if (amount <= 0) return -1
        for (invStack in inventory.contents) {
            if (invStack == null) continue
            if (amount == 0) return 1
            if (CustomItemStack(stack).isSimilar(invStack)) {
                if (invStack.amount <= amount) {
                    inventory.remove(invStack)
                    amount -= invStack.amount
                } else {
                    invStack.amount = invStack.amount - amount
                    amount = 0
                }
            }
        }
        return 1
    }
}