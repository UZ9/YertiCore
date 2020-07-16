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

    Inventory inventory;
    InventoryHolder holder;
    private boolean cancelEvent = false;
    private int slots;
    private String displayName;
    private Map<Integer, MenuItem> items = new HashMap<>();

    /**
     * Creates a CustomInventory off of arguments
     *
     * @param slots
     * @param displayName
     */
    public CustomInventory(InventoryHolder holder, int slots, String displayName) {
        this.slots = slots;
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.holder = holder == null ? this : holder;

        inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', displayName));
    }

    public CustomInventory(InventoryHolder holder, InventoryType type, String displayName) {
        this.slots = type.getDefaultSize();
        this.displayName = ChatUtils.style(displayName);
        this.holder = holder == null ? this : holder;

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

    public void fill(int start, int end, ItemStack stack) {
        for (int i = start; i < end; i++) {
            inventory.setItem(i, stack);
        }
    }



    public ItemStack getItem(int index) {
        return getInventory().getItem(index);
    }

    /**
     * Retrieves the inventory
     *
     * @return the inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }


    /**
     * Fetches the amount of slots in the inventory
     *
     * @return
     */
    public int getSlots() {
        return slots;
    }

    /**
     * Sets the amount of slots in the inventory
     *
     * @param slots
     */
    public void setSlots(int slots) {
        this.slots = slots;
    }

    /**
     * Retrieves the display name for the inventory
     *
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the items with events
     *
     * @return
     */
    public Map<Integer, MenuItem> getItems() {
        return items;
    }

    /**
     * Sets the display name for the inventory
     *
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setItem(int index, ItemStack stack) {
        getInventory().setItem(index, stack);
    }

    public void setItem(int index, MenuItem item) {
        items.put(index, item);
        getInventory().setItem(index, item);
    }

    public void addItem(ItemStack... item) {
        inventory.addItem(item);
    }



    public boolean cancelsEvent() {
        return cancelEvent;
    }

    public CustomInventory cancel(boolean v) {
        cancelEvent = v;
        return this;
    }
}
