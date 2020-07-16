package com.yerti.core;


import com.yerti.core.command.CommandFramework;
import com.yerti.core.enchantmenets.EnchantmentHandler;
import com.yerti.core.entity.ModelProtection;
import com.yerti.core.inventories.InventoryHandler;
import com.yerti.core.recipe.CustomRecipeHandler;
import com.yerti.core.utils.ChatUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for loading any additional resources with only calling one method
 * Main class extends YertiPlugin (which then extends JavaPlugin
 */
public abstract class YertiPlugin extends JavaPlugin {

    private static Plugin hookedPlugin;

    protected Class<?> commandClass;

    public static Plugin getHookedPlugin() {
        return hookedPlugin;
    }

    public static void addHookedPlugin(Plugin plugin) {
        hookedPlugin = plugin;
    }

    public final void onEnable() {
        getServer().getPluginManager().registerEvents(new ModelProtection(), this);
        getServer().getPluginManager().registerEvents(new CustomRecipeHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentHandler(), this);
        getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        onPluginEnable();
        new CommandFramework(this, commandClass);
    }

    public final void onDisable() {
        onPluginDisable();
    }

    public abstract void onPluginEnable();

    public abstract void onPluginDisable();

    protected void setPrefix(String prefix) {
        ChatUtils.setPrefix(prefix);
    }

}
