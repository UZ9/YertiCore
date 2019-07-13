package com.yerti.core;

import com.yerti.core.inventories.InventoryHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class YertiCore extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
    }

    @Override
    public void onDisable() {
    }

}
