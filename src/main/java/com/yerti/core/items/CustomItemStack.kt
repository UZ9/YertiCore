package com.yerti.core.items

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Creates a CustomItemStack for ease of access
 */
class CustomItemStack : ItemStack {
    var type: Material? = null
    var amount = 0

    /**
     * Creates a CustomItemStack off of parameters
     * @param stack
     */
    constructor(stack: ItemStack?) : super(stack) {}

    /**
     * Creates a CustomItemStack off of parameters
     * @param type Material of the ItemStack
     * @param amount Amount of the ItemStack
     * @param displayName Display name of the ItemStack
     * @param glowing Boolean for whether the item should be glowing
     * @param lore Lore for the ItemStack
     */
    constructor(type: Material?, amount: Int, displayName: String?, glowing: Boolean, vararg lore: String?) : this(type, amount, displayName, glowing) {
        val meta = itemMeta
        meta.lore = Arrays.asList<String>(*lore)
    }
    /**
     * Creates a CustomItemStack off of parameters
     * @param type Material of the ItemStack
     * @param amount Amount of the ItemStack
     * @param displayName Display name of the ItemStack
     * @param glowing Boolean for whether the item should be glowing
     */
    /**
     * Creates a CustomItemStack off of parameters
     * @param type
     * @param amount
     */
    @JvmOverloads
    constructor(type: Material?, amount: Int, displayName: String? = null, glowing: Boolean = false) : super(type, amount) {
        this.type = type
        this.amount = amount
        setType(type)
        val meta = itemMeta
        if (displayName != null) {
            meta.displayName = displayName
        }
        setAmount(amount)
        val stack = ItemStack(Material.STONE, 3, 3.toShort())
        if (glowing) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        this.itemMeta = meta
        if (glowing) {
            addUnsafeEnchantment(Enchantment.DIG_SPEED, 1)
        }
    }

    /**
     * Sets the name of the itemstack
     * @param text
     * @return
     */
    fun name(text: String?): CustomItemStack {
        val meta = itemMeta
        meta.displayName = ChatColor.translateAlternateColorCodes('&', text)
        itemMeta = meta
        return this
    }

    /**
     * Gets the lore of the itemstack at a specific index
     * @param index
     * @return
     */
    fun getLore(index: Int): String {
        val meta = itemMeta
        return meta.lore[index]
    }

    /**
     * Adds lore to a CustomItemStack
     * @param text
     * @return
     */
    fun lore(text: String?): CustomItemStack {
        val meta = itemMeta
        if (meta.lore == null) {
            meta.lore = ArrayList()
        }
        var lore = if (meta.lore == null) ArrayList() else meta.lore
        if (lore == null) lore = ArrayList()
        lore.add(ChatColor.translateAlternateColorCodes('&', text))
        meta.lore = lore
        itemMeta = meta
        return this
    }

    fun lore(vararg text: String?): CustomItemStack {
        for (string in text) {
            lore(string)
        }
        return this
    }

    /**
     * Adds lore to a CustomItemStack
     * @param text
     * @return
     */
    fun lore(index: Int, text: String?): CustomItemStack {
        val meta = itemMeta
        if (meta.lore == null) {
            meta.lore = ArrayList()
        }
        var lore = if (meta.lore == null) ArrayList() else meta.lore
        if (lore == null) lore = ArrayList()
        lore[index] = ChatColor.translateAlternateColorCodes('&', text)
        meta.lore = lore
        itemMeta = meta
        return this
    }

    /**
     * Applies a glow effect to the item
     * @return
     */
    fun glow(): CustomItemStack {
        enchant(Enchantment.ARROW_FIRE, 1)
        addFlag(ItemFlag.HIDE_ENCHANTS)
        return this
    }

    fun setNBT(key: String?, value: Any?): CustomItemStack {
        ItemMetaData.setMetadata(this, key, value)
        return this
    }

    /**
     * Adds an itemflag to the itemstack
     * @param flag
     * @return
     */
    fun addFlag(flag: ItemFlag?): CustomItemStack {
        val meta = itemMeta
        meta.addItemFlags(flag)
        itemMeta = meta
        return this
    }

    /**
     * Enchants the itemstack with an enchantment at a certain level
     * @param enchantment
     * @param level
     * @return
     */
    fun enchant(enchantment: Enchantment?, level: Int): CustomItemStack {
        val meta = itemMeta
        meta.addEnchant(enchantment, level, true)
        itemMeta = meta
        return this
    }

    /**
     * Sets the durability/damage of an itemstack
     * @param damage
     * @return
     */
    fun damage(damage: Int): CustomItemStack {
        durability = damage.toShort()
        return this
    }

    /**
     * Strips the lore of an itemstack
     * @return
     */
    fun stripLore(): CustomItemStack {
        val meta = itemMeta
        meta.lore = ArrayList()
        itemMeta = meta
        return this
    }

    /**
     * Removes the lore of the itemstack at a specific index
     * @param index
     * @return
     */
    fun removeLore(index: Int): CustomItemStack {
        val meta = itemMeta
        val lore = meta.lore
        lore.removeAt(index)
        meta.lore = lore
        itemMeta = meta
        return this
    }

    /**
     * Sets the amount of the itemstack
     * @param amount
     * @return
     */
    fun amount(amount: Int): CustomItemStack {
        setAmount(amount)
        return this
    }

    /**
     * Retrieves the displayname of the CustomItemStack
     * @return
     */
    val displayName: String
        get() = itemMeta.displayName

    fun getMetaInt(key: String?): Int {
        return ItemMetaData.getMetadata(this, key) as Int
    }

    fun getMetaString(key: String?): String? {
        return ItemMetaData.getMetadata(this, key) as String
    }

    fun getMetaFloat(key: String?): Float {
        return ItemMetaData.getMetadata(this, key) as Float
    }

    fun getMetaDouble(key: String?): Double {
        return ItemMetaData.getMetadata(this, key) as Double
    }

    fun hasMeta(key: String?): Boolean {
        return ItemMetaData.hasMetadata(this, key)
    }
}