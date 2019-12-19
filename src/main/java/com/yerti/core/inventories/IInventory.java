package com.yerti.core.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * Interface to be extended when creating a new custom inventory
 */
public interface IInventory extends InventoryHolder {

    /**
     * Issues that a clicked event happened for a gui
     * @param player
     * @param slot
     * @param clickedItem
     * @param event
     */
    void onGUI(Player player, int slot, ItemStack clickedItem, InventoryClickEvent event);

}
