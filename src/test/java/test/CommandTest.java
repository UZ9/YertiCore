package test;

import com.yerti.core.YertiPlugin;
import com.yerti.core.command.CustomCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Example of a command using the YertiCore {@link com.yerti.core.command.CustomCommand} annotations
 */
public class CommandTest extends YertiPlugin {

    /**
     * Normal override (JavaPlugin)
     */
    @Override
    public void onEnable() {

    }

    /**
     * Toggles the flying of a player using {@link com.yerti.core.command.CustomCommand} annotations
     * Command Annotation Parameters:
     * String name, String permission, String description, String usage, String[] aliases
     * SubCommand can be used the same way
     * @param sender CommandSender
     * @param command Command issued
     * @param args Arguments from command
     */
    @CustomCommand(name = "fly", permission = "admin.fly", description = "Let's you fly!", usage = "/fly", aliases = "")
    public void set(CommandSender sender, Command command, String[] args) {
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
