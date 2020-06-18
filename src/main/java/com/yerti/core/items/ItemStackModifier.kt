package com.yerti.core.items

import org.bukkit.inventory.ItemStack

/**
 * Modifier for ItemStacks
 */
class ItemStackModifier
/**
 * Creates an ItemStackModifier
 * @param stack
 */(var stack: ItemStack) {
    /**
     * Sets the display name of an ItemStack
     * @param displayName
     * @return
     */
    fun setDisplayName(displayName: String?): ItemStack {
        val meta = stack.itemMeta
        meta.displayName = displayName
        stack.itemMeta = meta
        return stack
    }

}