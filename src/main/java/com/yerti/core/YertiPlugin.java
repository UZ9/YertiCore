package com.yerti.core;


import com.yerti.core.command.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for loading any additional resources with only calling one method
 * Main class extends YertiPlugin (which then extends JavaPlugin
 */
public class YertiPlugin extends JavaPlugin {

    /**
     * Loads needed resources
     */
    protected void load(Class<?> commandClass) {
        new CommandFramework(this, commandClass);
    }

}
