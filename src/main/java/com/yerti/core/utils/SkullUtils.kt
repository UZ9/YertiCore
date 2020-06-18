package com.yerti.core.utils

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import java.net.URI
import java.net.URISyntaxException
import java.util.*

object SkullUtils {
    /**
     * Creates a player skull based on a player's name.
     *
     * @param name The Player's name
     * @return The head of the Player
     *
     */
    @Deprecated("names don't make for good identifiers")
    fun itemFromName(name: String): ItemStack {
        val item = playerSkullItem
        return itemWithName(item, name)
    }

    /**
     * Creates a player skull based on a player's name.
     *
     * @param item The item to apply the name to
     * @param name The Player's name
     * @return The head of the Player
     *
     */
    @Deprecated("names don't make for good identifiers")
    fun itemWithName(item: ItemStack?, name: String): ItemStack {
        notNull(item, "item")
        notNull(name, "name")
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:\"$name\"}"
        )
    }

    /**
     * Creates a player skull based on a Mojang server URL.
     *
     * @param url The URL of the Mojang skin
     * @return The head associated with the URL
     */
    fun itemFromUrl(url: String?): ItemStack {
        val item = playerSkullItem
        return itemWithUrl(item, url)
    }

    /**
     * Creates a player skull based on a Mojang server URL.
     *
     * @param item The item to apply the skin to
     * @param url The URL of the Mojang skin
     * @return The head associated with the URL
     */
    fun itemWithUrl(item: ItemStack?, url: String?): ItemStack {
        notNull(item, "item")
        notNull(url, "url")
        return itemWithBase64(item, urlToBase64(url))
    }

    /**
     * Creates a player skull based on a base64 string containing the link to the skin.
     *
     * @param base64 The base64 string containing the texture
     * @return The head with a custom texture
     */
    fun itemFromBase64(base64: String): ItemStack {
        val item = playerSkullItem
        return itemWithBase64(item, base64)
    }

    /**
     * Applies the base64 string to the ItemStack.
     *
     * @param item The ItemStack to put the base64 onto
     * @param base64 The base64 string containing the texture
     * @return The head with a custom texture
     */
    fun itemWithBase64(item: ItemStack?, base64: String): ItemStack {
        notNull(item, "item")
        notNull(base64, "base64")
        val hashAsId = UUID(base64.hashCode().toLong(), base64.hashCode().toLong())
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"$hashAsId\",Properties:{textures:[{Value:\"$base64\"}]}}}"
        )
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param url The mojang URL to set it to use
     */
    fun blockWithUrl(block: Block, url: String?) {
        notNull(block, "block")
        notNull(url, "url")
        blockWithBase64(block, urlToBase64(url))
    }

    /**
     * Sets the block to a skull with the given UUID.
     *
     * @param block The block to set
     * @param base64 The base64 to set it to use
     */
    fun blockWithBase64(block: Block, base64: String) {
        notNull(block, "block")
        notNull(base64, "base64")
        val hashAsId = UUID(base64.hashCode().toLong(), base64.hashCode().toLong())
        val args = String.format(
                "%d %d %d %s",
                block.x,
                block.y,
                block.z,
                "{Owner:{Id:\"$hashAsId\",Properties:{textures:[{Value:\"$base64\"}]}}}"
        )
        if (newerApi()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "data merge block $args")
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "blockdata $args")
        }
    }

    private fun newerApi(): Boolean {
        return try {
            Material.valueOf("PLAYER_HEAD")
            true
        } catch (e: IllegalArgumentException) { // If PLAYER_HEAD doesn't exist
            false
        }
    }

    private val playerSkullItem: ItemStack
        private get() = if (newerApi()) {
            ItemStack(Material.valueOf("PLAYER_HEAD"))
        } else {
            ItemStack(Material.valueOf("SKULL_ITEM"), 1, 3.toByte())
        }

    private fun setBlockType(block: Block) {
        try {
            block.setType(Material.valueOf("PLAYER_HEAD"), false)
        } catch (e: IllegalArgumentException) {
            block.setType(Material.valueOf("SKULL"), false)
        }
    }

    private fun notNull(o: Any?, name: String) {
        if (o == null) {
            throw NullPointerException("$name should not be null!")
        }
    }

    private fun urlToBase64(url: String?): String {
        val actualUrl: URI
        actualUrl = try {
            URI(url)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
        val toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"$actualUrl\"}}}"
        return Base64.getEncoder().encodeToString(toEncode.toByteArray())
    }
}