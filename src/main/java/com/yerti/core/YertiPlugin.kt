package com.yerti.core

import com.yerti.core.command.CommandFramework
import com.yerti.core.enchantmenets.EnchantmentHandler
import com.yerti.core.entity.ModelProtection
import com.yerti.core.inventories.InventoryHandler
import com.yerti.core.recipe.CustomRecipeHandler
import com.yerti.core.utils.ChatUtils
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

/**
 * Class for loading any additional resources with only calling one method
 * Main class extends YertiPlugin (which then extends JavaPlugin
 */
open class YertiPlugin : JavaPlugin() {
    private var commandClass: Class<*>? = null;
    override fun onEnable() {
        commandClass?.let { CommandFramework(this, it) }

        server.pluginManager.registerEvents(ModelProtection(), this)
        server.pluginManager.registerEvents(CustomRecipeHandler(), this)
        server.pluginManager.registerEvents(EnchantmentHandler(), this)
        server.pluginManager.registerEvents(InventoryHandler(), this)
    }

    /**
     * Loads needed resources
     */
    protected fun registerCommandClass(commandClass: Class<*>) {
        this.commandClass = commandClass
    }

    protected fun setPrefix(prefix: String) {
        ChatUtils.setPrefix(prefix)
    }

    companion object {
        @JvmStatic
        var hookedPlugin: Plugin? = null
            private set

        fun addHookedPlugin(plugin: Plugin?) {
            hookedPlugin = plugin
        }
    }
}