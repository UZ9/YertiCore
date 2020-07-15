package com.yerti.core.database.sql.types.mysql;

import com.yerti.core.database.sql.types.sqlite.SQLiteInfo;

public class MySQLInfo extends SQLiteInfo {

    private String ip;
    private String port;

    public MySQLInfo(String ip, String port, String database, String user, String pass) {
        super(database, user, pass);

        this.port = port;
        this.ip = ip;
    }



    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }
}
