package com.yerti.core.recipe;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomRecipeHandler implements Listener {


    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {

        CraftingInventory inventory = event.getInventory();

        if (event.getInventory().getResult() == null) return;
        if (CustomRecipe.getMatrixMap() == null) return;

        for (Map.Entry<ItemStack, ItemStack[]> entry : CustomRecipe.getMatrixMap().entrySet()) {


            if (inventory.getResult().equals(entry.getKey())) {

                for (int i = 0; i < 9; i++) {

                    if (inventory.getMatrix()[i].hasItemMeta()) {
                        if (!entry.getValue()[i].hasItemMeta()) {
                            return;
                        }
                    }


                    if (!inventory.getMatrix()[i].getItemMeta().equals(entry.getValue()[i].getItemMeta())) {
                        inventory.setResult(null);
                        return;
                    }
                }


            }

        }


    }

}
