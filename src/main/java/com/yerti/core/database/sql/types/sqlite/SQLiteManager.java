package com.yerti.core.database.sql.types.sqlite;

import com.yerti.core.database.sql.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager extends SQLManager {

    private File file;

    public SQLiteManager(JavaPlugin plugin, SQLiteInfo info, boolean debug) {
        super(plugin, debug);

        try {
            Class.forName("org.sqlite.JDBC");

            file = new File(plugin.getDataFolder(), info.getDatabase() + ".sqlite");

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }
}
