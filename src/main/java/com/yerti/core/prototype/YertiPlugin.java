package com.yerti.core.prototype;

import com.yerti.core.prototype.command.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for loading any additional resources with only calling one method
 * Main class extends YertiPlugin (which then extends JavaPlugin
 */
public class YertiPlugin extends JavaPlugin {

    /**
     * Loads needed resources
     */
    protected void load() {
        new CommandFramework(this);
    }

}
