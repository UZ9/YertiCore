package com.yerti.core.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * Similar Inventory system to {@link com.yerti.core.menus.Page} without using keys
 */
public class CustomInventory implements InventoryHolder {

    Inventory inventory;
    InventoryHolder holder;
    private int slots;
    private String displayName;
    private ItemStack backgroundItem;

    /**
     * Creates a CustomInventory off of arguments
     * @param holder
     * @param slots
     * @param displayName
     * @param backgroundItem item used for the background of the inventory
     */
    public CustomInventory(InventoryHolder holder, int slots, String displayName, ItemStack backgroundItem) {
        this.slots = slots;
        this.displayName = displayName;
        this.backgroundItem = backgroundItem;
        this.holder = holder;

        inventory = Bukkit.createInventory(holder, slots, displayName);
        createBackground();
    }

    /**
     * Creates the background for the inventory
     */
    public void createBackground() {
        for (int i = 0; i < slots; i++) {
            if (inventory.getContents()[i] == null || inventory.getContents()[i].getType().equals(Material.AIR)) {
                inventory.setItem(i, backgroundItem);
            }
        }
    }

    /**
     * Retrieves the inventory
     * @return the inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }


    /**
     * Fetches the amount of slots in the inventory
     * @return
     */
    public int getSlots() {
        return slots;
    }

    /**
     * Sets the amount of slots in the inventory
     * @param slots
     */
    public void setSlots(int slots) {
        this.slots = slots;
    }

    /**
     * Retrieves the display name for the inventory
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name for the inventory
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieves the background item for the inventory
     * @return
     */
    public ItemStack getBackgroundItem() {
        return backgroundItem;
    }

    /**
     * Sets the background item for the inventory
     * @param backgroundItem
     */
    public void setBackgroundItem(ItemStack backgroundItem) {
        this.backgroundItem = backgroundItem;
    }
}
