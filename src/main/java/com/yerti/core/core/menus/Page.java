package com.yerti.core.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {

    private Size size;
    private String displayName;
    private Map<Row, String> rowPatterns;
    private Map<Character, MenuItem> itemKeys;


    public Page(String displayName, Size size) {
        rowPatterns = new HashMap<>();
        itemKeys = new HashMap<>();

        this.size = size;
        this.displayName = displayName;

    }

    public Page setRowPattern(Row row, String pattern) {
        rowPatterns.put(row, pattern);
        return this;
    }

    public Page setKey(char character, MenuItem stack) {
        itemKeys.put(character, stack);
        return this;
    }

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

    //TODO: Update this
    public List<MenuItem> getMenuItems() {
        return Arrays.asList(itemKeys.values().toArray(new MenuItem[itemKeys.values().size()]));
    }


}
