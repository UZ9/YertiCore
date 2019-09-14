package com.yerti.core.core.items;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemstackModifier  {

    ItemStack stack;

     public ItemstackModifier(ItemStack stack) {
         this.stack = stack;
     }

    public ItemstackModifier setDisplayName(String displayName) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemstackModifier addLore(String lore) {
        ItemMeta meta = stack.getItemMeta();
        List<String> oldLore = meta.getLore();
        oldLore = oldLore == null ? new ArrayList<>() : oldLore;
        oldLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        meta.setLore(oldLore);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
         return stack;
    }

}
