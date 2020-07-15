package com.yerti.core.database;

import org.bukkit.plugin.java.JavaPlugin;

public class StorageManager {

    protected boolean debug;
    protected JavaPlugin plugin;

    public StorageManager(JavaPlugin plugin, boolean debug) {
        this.plugin = plugin;
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
