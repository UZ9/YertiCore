package com.yerti.core.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Modifier for ItemStacks
 */
public class ItemStackModifier {

    ItemStack stack;

    /**
     * Creates an ItemStackModifier
     *
     * @param stack
     */
    public ItemStackModifier(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Sets the display name of an ItemStack
     *
     * @param displayName
     * @return
     */
    public ItemStack setDisplayName(String displayName) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        return stack;
    }

}
