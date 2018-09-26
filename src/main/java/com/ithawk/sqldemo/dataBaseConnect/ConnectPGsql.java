package com.ithawk.sqldemo.dataBaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectPGsql {

    /**
     * 创建Postgre数据库连接
     *
     * @return
     */
    public Connection postgreSQLConnect() {
        try {
            String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "7707" + "/" + "postgre";
            Class.forName("org.postgresql.Driver");
            String password = "123456";
            return DriverManager.getConnection(url, "poot", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
