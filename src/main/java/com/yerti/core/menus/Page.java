package com.yerti.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page with key and pattern building
 */
public class Page {

    private Size size;
    private String displayName;
    private Map<Row, String> rowPatterns;
    private Map<Character, MenuItem> itemKeys;

    /**
     * Creates a page with a displayname and size
     * @param displayName
     * @param size
     * For size, {@link Size}
     */
    public Page(String displayName, Size size) {
        rowPatterns = new HashMap<>();
        itemKeys = new HashMap<>();

        this.size = size;
        this.displayName = displayName;

    }

    /**
     * Sets the row pattern for a specific row
     * @param row for the pattern
     * @param pattern for the specific row
     * @return
     */
    public Page setRowPattern(Row row, String pattern) {
        rowPatterns.put(row, pattern);
        return this;
    }

    /**
     * Sets the key for a specific character in the pattern
     * @param character to set the MenuItem to
     * @param stack the {@link MenuItem} for the character
     * @return the page for further modification
     */
    public Page setKey(char character, MenuItem stack) {
        itemKeys.put(character, stack);
        return this;
    }

    /**
     * Builds the inventory off of the patterns and keys
     * If a key is not defined for a particular character, the slot will be set to air
     * @return the result inventory
     */
    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, (size.slotAmount), displayName);

        for (Row row : rowPatterns.keySet()) {
            if (rowPatterns.get(row).toCharArray().length > 9) {
                throw new IndexOutOfBoundsException("Character amount was large than 9 using setRowPattern");
            }

            int slot = 0;
            for (char character : rowPatterns.get(row).toCharArray()) {
                if (itemKeys.get(character) == null || itemKeys.get(character).equals(' ')) {
                    inventory.setItem(slot, new ItemStack(Material.AIR));

                } else {
                    inventory.setItem(slot, itemKeys.get(character));
                }
                slot++;
            }
        }


        return inventory;
    }

    //TODO: Update this to be more efficient and compact
    /**
     * Retrieves all of the menu items on the page
     * @return a list of {@link MenuItem}s
     */
    public List<MenuItem> getMenuItems() {
        return Arrays.asList(itemKeys.values().toArray(new MenuItem[0]));
    }


}
