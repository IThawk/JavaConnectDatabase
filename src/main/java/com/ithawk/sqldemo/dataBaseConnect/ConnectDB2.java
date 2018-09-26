package com.ithawk.sqldemo.dataBaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB2 {
    /***
     * 创建DB2数据库连接
     *
     * @param
     * @return
     */
    public Connection db2Connect() {
        Connection conn = null;
        try {
            Class.forName("com.ibm.db2.jdbc.net.DB2Driver");
            String password = "123456";
            conn = DriverManager.getConnection("jdbc:db2://" + "127.0.0.1"+ ":" + "6760" +
                    "/" + "master", "root", password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
