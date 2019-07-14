package com.yerti.core.items;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemStack extends ItemStack {


    public CustomItemStack(Material type, int amount) {
        this(type, amount, null, false);
    }


    public CustomItemStack(Material type, int amount, String displayName, boolean glowing) {
        this.setType(type);
        ItemMeta meta = getItemMeta();
        if (displayName != null) {

            meta.setDisplayName(displayName);
        }
        this.setAmount(amount);



        if (glowing) {
            this.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        this.setItemMeta(meta);
    }





}
