package com.yerti.core.database.sql.types.sqlite;

public class SQLiteInfo {

    private final String database;
    private final String user;
    private final String pass;

    public SQLiteInfo(String database, String user, String pass) {
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
