package com.yerti.core.items;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class CustomItemStack extends ItemStack {


    public CustomItemStack(Material type, int amount, String displayName, boolean glowing) {
        this.setType(type);
        this.setAmount(amount);
        this.getItemMeta().setDisplayName(displayName);

        if (glowing) {
            this.addEnchantment(Enchantment.DIG_SPEED, 1);
            this.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
    }




}
