package com.yerti.core.inventories;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Listener used for IInventory menus
 */
public class InventoryHandler implements Listener {

    /**
     * Handles the CustomInventory onClick events
     * @param event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof IInventory) {
            //Inventory is one of ours
            event.setCancelled(true);
            IInventory inventory = (IInventory) event.getInventory().getHolder();
            inventory.onGUI((Player) event.getWhoClicked(), event.getRawSlot(), event.getCurrentItem(), event);
        }

        if (event.getInventory().getHolder() instanceof CustomInventory) {
            CustomInventory inventory = (CustomInventory) event.getInventory().getHolder();
            if (inventory.cancelsEvent() && event.getRawSlot() < 27) event.setCancelled(true);
            System.out.println(inventory.getItems().size());
            if (inventory.getItems().containsKey(event.getSlot())) {
                System.out.println("Found");

                inventory.getItems().get(event.getSlot()).onClick(event);
            }

        }
    }


}
