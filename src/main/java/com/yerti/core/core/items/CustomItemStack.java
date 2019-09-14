package com.yerti.core.core.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomItemStack extends ItemStack {

    Material type;
    int amount;


    public CustomItemStack(Material type, int amount) {
        this(type, amount, null, false);
    }



    public CustomItemStack(Material type, int amount, String displayName, boolean glowing, String... lore) {
        this(type, amount, displayName, glowing);

        ItemMeta meta = getItemMeta();
        meta.setLore(Arrays.asList(lore));
    }

    public CustomItemStack lore(String text) {
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', text));
        meta.setLore(lore);
        setItemMeta(meta);

        return this;
    }

    public CustomItemStack(Material type, int amount, String displayName, boolean glowing) {

        this.type = type;
        this.amount = amount;
        this.setType(type);
        ItemMeta meta = getItemMeta();
        if (displayName != null) {

            meta.setDisplayName(displayName);
        }
        this.setAmount(amount);

        ItemStack stack = new ItemStack(Material.STONE, 3, (short) 3);



        if (glowing) {

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        this.setItemMeta(meta);

        if (glowing) {
            this.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
        }
    }

    public ItemStack getStack() {
        return this;
    }

    public String getDisplayName() {
        return getItemMeta().getDisplayName();
    }





}
