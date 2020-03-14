package com.yerti.core.inventories;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Listener used for IInventory menus
 */
public class InventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof IInventory) {
            //Inventory is one of ours
            event.setCancelled(true);
            IInventory inventory = (IInventory) event.getInventory().getHolder();
            inventory.onGUI((Player) event.getWhoClicked(), event.getRawSlot(), event.getCurrentItem(), event);
        }

        if (event.getWhoClicked().getOpenInventory().getTopInventory() != null && event.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof CustomInventory) {
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }
        }

        if (event.getInventory().getHolder() instanceof CustomInventory) {
            CustomInventory inventory = (CustomInventory) event.getInventory().getHolder();
            if (inventory.cancelsEvent() && event.getRawSlot() < inventory.getSlots()) event.setCancelled(true);
            System.out.println(inventory.getItems().size());
            if (inventory.getItems().containsKey(event.getSlot())) {
                inventory.getItems().get(event.getSlot()).onClick(event);
            }

        }
    }


}
