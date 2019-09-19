package com.yerti.test;

import com.yerti.core.prototype.YertiPlugin;
import com.yerti.core.prototype.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Example of a command using the YertiCore {@link com.yerti.core.prototype.command.Command} annotations
 */
public class CommandTest extends YertiPlugin {

    /**
     * Normal override (JavaPlugin)
     */
    @Override
    public void onEnable() {
        load();
    }

    /**
     * Toggles the flying of a player using {@link com.yerti.core.prototype.command.Command} annotations
     * Command Annotation Parameters:
     * String name, String permission, String description, String usage, String[] aliases
     * SubCommand can be used the same way
     * @param sender CommandSender
     * @param command Command issued
     * @param args Arguments from command
     */
    @Command(name = "fly", permission = "admin.fly", description = "Let's you fly!", usage = "/fly", aliases = "")
    public void set(CommandSender sender,  Command command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isFlying()) {
                player.setFlying(false);
            } else {
                player.setFlying(true);
            }
        }
    }

}
