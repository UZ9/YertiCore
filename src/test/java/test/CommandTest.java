package test;

import com.yerti.core.YertiPlugin;
import com.yerti.core.command.CustomCommand;
import com.yerti.core.database.sql.types.mysql.MySQLInfo;
import com.yerti.core.database.sql.types.mysql.MySQLManager;
import com.yerti.core.database.sql.types.sqlite.SQLiteInfo;
import com.yerti.core.database.sql.types.sqlite.SQLiteManager;
import com.yerti.core.localization.Localization;
import com.yerti.core.localization.LocalizationType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

/**
 * Example of a command using the YertiCore {@link com.yerti.core.command.CustomCommand} annotations
 */
public class CommandTest extends YertiPlugin {

    /**
     * Normal override (JavaPlugin)
     */
    @Override
    public void onEnable() {
        MySQLManager manager = new MySQLManager(this,
                new MySQLInfo("localhost", "25565", "exampledatabase", "user", "pass"),
                true);

        SQLiteManager sqLiteManager = new SQLiteManager(this,
                new SQLiteInfo("db", "user", "pass"), false);


        ResultSet example = manager.query("SELECT * FROM exampleTable WHERE id = ?");
        ResultSet example2 = sqLiteManager.query("SELECT * FROM exampleTable where id = ?");

        //Localization
        Localization localization = new Localization(this, LocalizationType.EN_US);

        String pickaxeName = localization.getValue("pickaxe.name");


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
