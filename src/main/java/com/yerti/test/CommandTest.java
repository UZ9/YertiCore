package com.yerti.test;

import com.yerti.core.prototype.YertiPlugin;
import com.yerti.core.prototype.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTest extends YertiPlugin {

    @Override
    public void onEnable() {
        load();
    }

    @Command(name = "fly", permission = "admin.fly", description = "Let's you fly!", usage = "/fly", aliases = "")
    public void set(CommandSender sender,  Command command, String[] args) {

    }

}
