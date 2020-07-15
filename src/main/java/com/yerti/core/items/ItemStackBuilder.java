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
public class ItemStackBuilder extends ItemStack {

    Material type;
    int amount;

    /**
     * Creates a CustomItemStack off of parameters
     *
     * @param type
     * @param amount
     */
    public ItemStackBuilder(Material type, int amount) {
        this(type, amount, null, false);
    }

    /**
     * Creates a CustomItemStack off of parameters
     *
     * @param stack
     */
    public ItemStackBuilder(ItemStack stack) {
        super(stack);
    }

    /**
     * Creates a CustomItemStack off of parameters
     *
     * @param type        Material of the ItemStack
     * @param amount      Amount of the ItemStack
     * @param displayName Display name of the ItemStack
     * @param glowing     Boolean for whether the item should be glowing
     * @param lore        Lore for the ItemStack
     */
    public ItemStackBuilder(Material type, int amount, String displayName, boolean glowing, String... lore) {
        this(type, amount, displayName, glowing);

        ItemMeta meta = getItemMeta();
        meta.setLore(Arrays.asList(lore));
    }

    /**
     * Creates a CustomItemStack off of parameters
     *
     * @param type        Material of the ItemStack
     * @param amount      Amount of the ItemStack
     * @param displayName Display name of the ItemStack
     * @param glowing     Boolean for whether the item should be glowing
     */
    public ItemStackBuilder(Material type, int amount, String displayName, boolean glowing) {
        super(type, amount);


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
     * Sets the name of the itemstack
     *
     * @param text
     * @return
     */
    public ItemStackBuilder name(String text) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', text));
        setItemMeta(meta);
        return this;
    }

    /**
     * Gets the lore of the itemstack at a specific index
     *
     * @param index
     * @return
     */
    public String getLore(int index) {
        ItemMeta meta = getItemMeta();
        return meta.getLore().get(index);
    }

    /**
     * Adds lore to a CustomItemStack
     *
     * @param text
     * @return
     */
    public ItemStackBuilder lore(String text) {
        ItemMeta meta = getItemMeta();


        if (meta.getLore() == null) {
            meta.setLore(new ArrayList<>());
        }

        List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();


        if (lore == null) lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', text));
        meta.setLore(lore);
        setItemMeta(meta);

        return this;
    }

    public ItemStackBuilder lore(String... text) {
        for (String string : text) {
            lore(string);
        }

        return this;
    }

    /**
     * Adds lore to a CustomItemStack
     *
     * @param text
     * @return
     */
    public ItemStackBuilder lore(int index, String text) {
        ItemMeta meta = getItemMeta();


        if (meta.getLore() == null) {
            meta.setLore(new ArrayList<>());
        }

        List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();


        if (lore == null) lore = new ArrayList<>();
        lore.set(index, ChatColor.translateAlternateColorCodes('&', text));
        meta.setLore(lore);
        setItemMeta(meta);

        return this;
    }

    /**
     * Applies a glow effect to the item
     *
     * @return
     */
    public ItemStackBuilder glow() {
        enchant(Enchantment.ARROW_FIRE, 1);
        addFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStackBuilder setNBT(String key, Object value) {
        ItemMetaData.setMetadata(this, key, value);
        return this;
    }

    /**
     * Adds an itemflag to the itemstack
     *
     * @param flag
     * @return
     */
    public ItemStackBuilder addFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }

    /**
     * Enchants the itemstack with an enchantment at a certain level
     *
     * @param enchantment
     * @param level
     * @return
     */
    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    /**
     * Sets the durability/damage of an itemstack
     *
     * @param damage
     * @return
     */
    public ItemStackBuilder damage(int damage) {
        setDurability((short) damage);
        return this;
    }

    /**
     * Strips the lore of an itemstack
     *
     * @return
     */
    public ItemStackBuilder stripLore() {
        ItemMeta meta = getItemMeta();
        meta.setLore(new ArrayList<>());
        setItemMeta(meta);
        return this;
    }

    /**
     * Removes the lore of the itemstack at a specific index
     *
     * @param index
     * @return
     */
    public ItemStackBuilder removeLore(int index) {
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();
        lore.remove(index);
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    /**
     * Sets the amount of the itemstack
     *
     * @param amount
     * @return
     */
    public ItemStackBuilder amount(int amount) {
        this.setAmount(amount);
        return this;
    }


    /**
     * Retrieves the displayname of the CustomItemStack
     *
     * @return
     */
    public String getDisplayName() {
        return getItemMeta().getDisplayName();
    }

    public int getMetaInt(String key) {
        return (int) ItemMetaData.getMetadata(this, key);
    }

    public String getMetaString(String key) {
        return (String) ItemMetaData.getMetadata(this, key);
    }

    public float getMetaFloat(String key) {
        return (float) ItemMetaData.getMetadata(this, key);
    }

    public double getMetaDouble(String key) {
        return (double) ItemMetaData.getMetadata(this, key);
    }

    public boolean hasMeta(String key) {
        return ItemMetaData.hasMetadata(this, key);
    }


}
