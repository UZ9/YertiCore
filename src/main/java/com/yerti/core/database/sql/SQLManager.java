package com.yerti.core.database.sql;

import com.yerti.core.database.StorageManager;
import com.yerti.core.utils.DBUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SQLManager extends StorageManager {

    /* SQL connection related variables */
    protected ResultSet resultSet;
    protected PreparedStatement preparedStatement;
    protected Connection connection;

    public SQLManager(JavaPlugin plugin, boolean debug) {
        super(plugin, debug);

        this.debug = debug;
    }

    protected abstract Connection getConnection() throws SQLException;

    public void execute(PreparedStatement ps) {
        try {
            this.preparedStatement = ps;
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void execute(String param) {
        execute(getStatement(param));
    }

    public void executeUpdate(PreparedStatement ps) {
        try {
            this.connection = getConnection();
            preparedStatement = ps;
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void executeUpdate(String param) {
        executeUpdate(getStatement(param));
    }

    public ResultSet query(PreparedStatement ps) {
        try {
            this.connection = getConnection();
            this.preparedStatement = ps;
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    public ResultSet query(String param) {
        return query(getStatement(param));
    }

    public PreparedStatement getStatement(String text) {
        try {
            this.connection = getConnection();
            return connection.prepareStatement(text);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    private void close() {
        DBUtils.closeQuiet(resultSet);
        DBUtils.closeQuiet(preparedStatement);
        DBUtils.closeQuiet(connection);
    }
}
