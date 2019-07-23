package com.yerti.core.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class ItemStackUtils {

    public static ItemStack getPotionItemStack(PotionType type, int level, boolean extend, String displayName){
        Potion potion = new Potion(type, level, false);
        if (extend) potion.setHasExtendedDuration(true);
        ItemStack itemstack = potion.toItemStack(1);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(displayName);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack getPotionItemStack(PotionType type, int level, boolean extend, String displayName, String... lore){
        Potion potion = new Potion(type, level, false);
        if (extend) potion.setHasExtendedDuration(true);
        ItemStack itemstack = potion.toItemStack(1);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        itemstack.setItemMeta(meta);
        return itemstack;
    }
}
