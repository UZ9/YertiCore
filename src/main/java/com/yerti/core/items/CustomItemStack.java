package com.yerti.core.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creates a CustomItemStack for ease of access
 */
public class CustomItemStack extends ItemStack {

    Material type;
    int amount;

    /**
     * Creates a CustomItemStack off of parameters
     * @param type
     * @param amount
     */
    public CustomItemStack(Material type, int amount) {
        this(type, amount, null, false);
    }

    /**
     * Creates a CustomItemStack off of parameters
     * @param stack
     */
    public CustomItemStack(ItemStack stack) {
        super(stack);
    }

    /**
     * Creates a CustomItemStack off of parameters
     * @param type Material of the ItemStack
     * @param amount Amount of the ItemStack
     * @param displayName Display name of the ItemStack
     * @param glowing Boolean for whether the item should be glowing
     * @param lore Lore for the ItemStack
     */
    public CustomItemStack(Material type, int amount, String displayName, boolean glowing, String... lore) {
        this(type, amount, displayName, glowing);

        ItemMeta meta = getItemMeta();
        meta.setLore(Arrays.asList(lore));
    }

    /**
     * Creates a CustomItemStack off of parameters
     * @param type Material of the ItemStack
     * @param amount Amount of the ItemStack
     * @param displayName Display name of the ItemStack
     * @param glowing Boolean for whether the item should be glowing
     */
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

    /**
     * Adds lore to a CustomItemStack
     * @param text
     * @return
     */
    public CustomItemStack lore(String text) {
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', text));
        meta.setLore(lore);
        setItemMeta(meta);

        return this;
    }

    /**
     * Retrieves the displayname of the CustomItemStack
     * @return
     */
    public String getDisplayName() {
        return getItemMeta().getDisplayName();
    }





}
