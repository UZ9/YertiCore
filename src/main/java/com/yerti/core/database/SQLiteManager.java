package com.yerti.core.database;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.*;

public class SQLiteManager {

    File file;
    private Connection connection;

    public SQLiteManager(Plugin plugin, String database, String user, String pass) {

        try {
            Class.forName("org.sqlite.JDBC");

            file = new File(plugin.getDataFolder(), database + ".sqlite");

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void execute(String param) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            Statement statement = connection.createStatement();
            statement.execute(param);
            statement.close();
            this.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String param) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            Statement statement = connection.createStatement();
            statement.executeUpdate(param);
            statement.close();
            this.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String param) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(param);
            statement.close();
            this.connection.close();
            return set;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
