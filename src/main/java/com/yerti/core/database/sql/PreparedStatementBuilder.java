package com.yerti.core.database.sql;

import com.yerti.core.logging.Logger;
import com.yerti.core.utils.DBUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class PreparedStatementBuilder {

    private final SQLManager manager;
    private final PreparedStatement statement;

    public PreparedStatementBuilder(SQLManager manager, String text) {
        this.manager = manager;
        this.statement = manager.getStatement(text);
    }

    public PreparedStatementBuilder setString(int index, String value) {
        try {
            statement.setString(index, value);
        } catch (SQLException e) {
            printStackTrace(e);
        }
        return this;
    }

    public PreparedStatementBuilder setNumber(int index, int value) {
        try {
            statement.setDouble(index, value);
        } catch (SQLException e) {
            printStackTrace(e);
        }
        return this;
    }

    public void execute() {
        try {
            statement.execute();
        } catch (SQLException e) {
            printStackTrace(e);
        } finally {
            DBUtils.closeQuiet(statement);
        }
    }

    private void printStackTrace(SQLException e) {
        if (manager.isDebug()) {
            e.printStackTrace();
        } else {
            Logger.log(Level.SEVERE, "A SQL exception has occured. Please enable debug mode for more information.");
        }
    }


}
