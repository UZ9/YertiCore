package com.yerti.core.inventories;


import com.yerti.core.menus.MenuItem;
import com.yerti.core.menus.Page;
import com.yerti.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Similar Inventory system to {@link Page} without using keys
 */
public class CustomInventory implements InventoryHolder {

    /**
     * Target inventory that is modified
     */
    private Inventory inventory;

    /**
     * Boolean for whether the itemclickevent should be cancelled
     */
    private boolean cancelEvent = false;

    /**
     * Variable for storing the amount of slots in the inventory
     */
    private int slots;

    /**
     * Variable for storing the displayname of the CustomInventory
     */
    private String displayName;

    /**
     * Map for storing the slots and their events (MenuItem handling)
     */
    private Map<Integer, MenuItem> items = new HashMap<>();

    /**
     * Creates a CustomInventory off of arguments
     * @param slots
     * @param displayName
     */
    public CustomInventory(int slots, String displayName) {
        this.slots = slots;
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);

        inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', displayName));
    }

    /**
     * Creates a CustomInventory off of arguments
     * @param type
     * @param displayName
     */
    public CustomInventory(InventoryType type, String displayName) {
        this.slots = type.getDefaultSize();
        this.displayName = ChatUtils.translate(displayName);

        this.inventory = Bukkit.createInventory(this, type, displayName);
    }

    /**
     * Creates the background for the inventory
     */
    public void createBackground(ItemStack backgroundItem) {
        for (int i = 0; i < slots; i++) {
            if (inventory.getContents()[i] == null || inventory.getContents()[i].getType().equals(Material.AIR)) {
                inventory.setItem(i, backgroundItem);
            }
        }
    }

    /**
     * Fills the inventory with an itemstack with a start and end
     * @param start
     * @param end
     * @param stack
     */
    public void fill(int start, int end, ItemStack stack) {
        for (int i = start; i < end; i++) {
            inventory.setItem(i, stack);
        }
    }

    /**
     * Sets an itemstack at a specific index
     * @param index
     * @param stack
     */
    public void setItem(int index, ItemStack stack) {
        getInventory().setItem(index, stack);
    }

    /**
     * Sets a menuitem with an event at a specific index
     * @param index
     * @param item
     */
    public void setItem(int index, MenuItem item) {
        items.put(index, item);
        getInventory().setItem(index, item);
    }

    /**
     * Retrieves an item at a specific index
     * @param index
     * @return
     */
    public ItemStack getItem(int index) {
        return getInventory().getItem(index);
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
     * Gets the items with events
     * @return
     */
    public Map<Integer, MenuItem> getItems() {
        return items;
    }

    public boolean cancelsEvent() {
        return cancelEvent;
    }

    public CustomInventory cancel(boolean v) {
        cancelEvent = v;
        return this;
    }
}
