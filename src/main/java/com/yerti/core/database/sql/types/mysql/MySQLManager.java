package com.yerti.core.database.sql.types.mysql;

import com.yerti.core.database.sql.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TODO: Move class from StockMarket plugin to here
public class MySQLManager extends SQLManager {

    private Connection con = null;
    private MySQLInfo info;

    public MySQLManager(JavaPlugin plugin, MySQLInfo info, boolean debug) {
        super(plugin, debug);

        this.info = info;


        String mysqlDB = info.getDatabase();
        String mysqlUser = info.getUser();
        String mysqlPW = info.getPass();


        final String driver = "com.mysql.jdbc.Driver";
        String connection = "jdbc:mysql://" + info.getIp() + ":" + info.getPort()+ "/" + mysqlDB;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(connection, mysqlUser, mysqlPW);

        } catch (SQLException e) {
            try {
                connection = "jdbc:mysql://" + info.getIp() + ":" + info.getPort();
                con = DriverManager.getConnection(connection, mysqlUser, mysqlPW);

                execute("CREATE DATABASE IF NOT EXISTS " + mysqlDB);

                connection = "jdbc:mysql://" + info.getIp() + ":" + info.getPort() + "/" + mysqlDB;
                con = DriverManager.getConnection(connection, mysqlUser, mysqlPW);

            } catch (SQLException e1) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + info.getIp() + ":" + info.getPort() + "/" + info.getDatabase(), info.getUser(), info.getPass());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
