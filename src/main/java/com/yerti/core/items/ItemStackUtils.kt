package com.yerti.core.items

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Contains several [ItemStack] utilities for ease
 */
object ItemStackUtils {
    private val parser = JsonParser()
    private val gson = GsonBuilder().create()
    /**
     * @return the smelted [ItemStack] variant of the {@param itemStack}
     */
    fun getSmeltedItemStack(itemStack: ItemStack): ItemStack? {
        return when (itemStack.type) {
            Material.PORK -> {
                ItemStack(Material.GRILLED_PORK, itemStack.amount)
            }
            Material.RAW_BEEF -> {
                ItemStack(Material.COOKED_BEEF, itemStack.amount)
            }
            Material.RAW_CHICKEN -> {
                ItemStack(Material.COOKED_CHICKEN, itemStack.amount)
            }
            Material.RAW_FISH -> {
                if (itemStack.durability > 1) {
                    null
                } else ItemStack(Material.COOKED_FISH, itemStack.amount, itemStack.durability)
            }
            Material.POTATO_ITEM -> {
                ItemStack(Material.BAKED_POTATO, itemStack.amount)
            }
            Material.IRON_ORE -> {
                ItemStack(Material.IRON_INGOT, itemStack.amount)
            }
            Material.GOLD_ORE -> {
                ItemStack(Material.GOLD_INGOT, itemStack.amount)
            }
            Material.SAND -> {
                ItemStack(Material.GLASS, itemStack.amount)
            }
            Material.COBBLESTONE -> {
                ItemStack(Material.STONE, itemStack.amount)
            }
            Material.CLAY_BALL -> {
                ItemStack(Material.CLAY_BRICK, itemStack.amount)
            }
            Material.NETHERRACK -> {
                ItemStack(Material.NETHER_BRICK_ITEM, itemStack.amount)
            }
            Material.CLAY -> {
                ItemStack(Material.HARD_CLAY, itemStack.amount)
            }
            Material.DIAMOND_ORE -> {
                ItemStack(Material.DIAMOND, itemStack.amount)
            }
            Material.LAPIS_ORE -> {
                ItemStack(Material.INK_SACK, itemStack.amount, 4.toShort())
            }
            Material.REDSTONE_ORE -> {
                ItemStack(Material.REDSTONE, itemStack.amount)
            }
            Material.COAL_ORE -> {
                ItemStack(Material.COAL, itemStack.amount)
            }
            Material.EMERALD_ORE -> {
                ItemStack(Material.EMERALD, itemStack.amount)
            }
            Material.QUARTZ_ORE -> {
                ItemStack(Material.QUARTZ, itemStack.amount)
            }
            Material.LOG -> {
                ItemStack(Material.COAL, itemStack.amount, 1.toShort())
            }
            Material.LOG_2 -> {
                ItemStack(Material.COAL, itemStack.amount, 1.toShort())
            }
            Material.CACTUS -> {
                ItemStack(Material.INK_SACK, itemStack.amount, 2.toShort())
            }
            Material.SMOOTH_BRICK -> {
                ItemStack(Material.SMOOTH_BRICK, itemStack.amount, 2.toShort())
            }
            Material.SPONGE -> {
                if (itemStack.durability < 1) {
                    null
                } else ItemStack(Material.SPONGE, itemStack.amount, 0.toShort())
            }
            Material.MUTTON -> {
                ItemStack(Material.COOKED_MUTTON, itemStack.amount)
            }
            Material.RABBIT -> {
                ItemStack(Material.COOKED_RABBIT, itemStack.amount)
            }
            else -> {
                null
            }
        }
    }

    fun serializeItemStack(item: ItemStack): JsonObject {
        val json = JsonObject()
        json.addProperty("type", item.type.name)
        json.addProperty("data", item.data.data)
        json.addProperty("amount", item.amount)
        json.addProperty("durability", item.durability)
        if (item.enchantments.size > 0) {
            val enchantments = JsonObject()
            for ((ench, lvl) in item.enchantments) {
                enchantments.addProperty(ench.name, lvl)
            }
            json.add("enchantments", enchantments)
        }
        if (item.hasItemMeta()) {
            val meta = item.itemMeta
            val itemmeta = JsonObject()
            if (meta.hasDisplayName()) {
                itemmeta.addProperty("displayname", meta.displayName)
            }
            if (meta.hasLore()) {
                itemmeta.add("lore", gson.toJsonTree(meta.lore))
            }
            if (meta.itemFlags.size > 0) {
                val serializedFlags = StringBuilder()
                for (flag in meta.itemFlags) {
                    serializedFlags.append(flag.toString()).append("@@")
                }
                itemmeta.addProperty("flags", serializedFlags.toString())
            }
            json.add("itemmeta", itemmeta)
        }
        return json
    }

    fun deserializeItemStack(string: String?): ItemStack? {
        return try {
            val json = parser.parse(string) as JsonObject
            val type = Material.getMaterial(json["type"].asString)
            val data = json["data"].asByte
            val amount = json["amount"].asInt
            val durability = json["durability"].asShort
            val item = ItemStack(type, amount, data.toShort())
            item.durability = durability
            if (json.has("enchantments")) {
                val enchantments = json.getAsJsonObject("enchantments")
                for ((key, value) in enchantments.entrySet()) {
                    item.addUnsafeEnchantment(Enchantment.getByName(key), value.asInt)
                }
            }
            if (json.has("itemmeta")) {
                val itemmeta = json.getAsJsonObject("itemmeta")
                val meta = item.itemMeta
                if (itemmeta.has("displayname")) {
                    meta.displayName = itemmeta["displayname"].asString
                }
                if (itemmeta.has("lore")) {
                    val lore: MutableList<String> = ArrayList()
                    val it: Iterator<JsonElement> = itemmeta["lore"].asJsonArray.iterator()
                    while (it.hasNext()) {
                        lore.add(it.next().asString)
                    }
                    meta.lore = lore
                }
                if (itemmeta.has("flags")) {
                    for (flag in itemmeta["flags"].asString.split("@@").toTypedArray()) {
                        if (flag == "") continue
                        meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()))
                    }
                }
                item.itemMeta = meta
            }
            item
        } catch (ex: Exception) {
            null
        }
    }
}