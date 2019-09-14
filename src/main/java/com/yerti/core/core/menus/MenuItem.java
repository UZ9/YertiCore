package com.yerti.core.core.menus;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem extends ItemStack {

    Consumer<InventoryClickEvent> event;

    public MenuItem(Material material, int amount, Consumer<InventoryClickEvent> event) {
        super(material, amount);
        this.event = event;
    }

    public void onClick(InventoryClickEvent e) {
        event.accept(e);
    }

    public ItemStack toStack() {
        return this;
    }

}
