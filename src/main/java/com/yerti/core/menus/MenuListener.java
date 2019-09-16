package com.yerti.core.menus;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener for all of the menu items
 */
public class MenuListener implements Listener {

    List<Page> menus;
    List<String> inventories;

    /**
     * Creates a MenuListener off of a list of {@link com.yerti.core.menus.Page}s
     * @param menus
     */
    public MenuListener(List<Page> menus) {
        this.menus = menus;
        this.inventories = new ArrayList<>();
        menus.forEach(menu -> inventories.add(menu.build().getName()));
    }

    /**
     * Checks for any specific {@link com.yerti.core.menus.MenuItem}s and their designated events
     * @param event
     */
    @EventHandler
    //TODO: Change to contains inventory rather than name
    public void onPlayerClick(InventoryClickEvent event) {
        if (event.getInventory() == null) return;
        if (!inventories.contains(event.getInventory().getName())) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);

        if (event.getCurrentItem().getItemMeta() == null) return;

        Page menu = menus.get(inventories.indexOf(event.getInventory().getName()));

        menu.getMenuItems().forEach(menuItem -> {
            if (event.getCurrentItem().getItemMeta().equals(menuItem.getItemMeta())) {
                if (event.getCurrentItem().getType().equals(Material.AIR)) return;
                menuItem.onClick(event);
            }
        });



    }


}
