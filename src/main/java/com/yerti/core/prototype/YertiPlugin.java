package com.yerti.core.prototype;

import com.yerti.core.prototype.command.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

public class YertiPlugin extends JavaPlugin {

    protected void load() {
        new CommandFramework(this);
    }

}
