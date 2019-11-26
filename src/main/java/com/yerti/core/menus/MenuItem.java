package com.yerti.core.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Custom ItemStack that has its own custom event when clicked
 */
public class MenuItem extends ItemStack {

    /**
     * Action for when inventory click occurs
     */
    private Consumer<InventoryClickEvent> event;


    /**
     * Creates a MenuItem off of a material, amount, and an action for the inventory click event.
     * @param stack {@link ItemStack} the ItemStack to be used
     * @param event the action for when inventory click occurs
     */
    public MenuItem(ItemStack stack, Consumer<InventoryClickEvent> event) {
        super(stack);
        this.event = event;
    }

    /**
     * Method for when the inventory click occurs
     * @param e
     */
    void onClick(final InventoryClickEvent e) {
        event.accept(e);
    }


}
