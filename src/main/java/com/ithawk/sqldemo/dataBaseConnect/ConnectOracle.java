package com.ithawk.sqldemo.dataBaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectOracle {
    public void testOracle() {
        Connection con;
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            String userName = "sys";

            userName = userName + " as sysdba";

            String password = "12345";
            con = DriverManager.getConnection("jdbc:oracle:thin:@" + "172.19.1.236" + ":" + "61510" + "/"
                    + "orcl", userName, password);

        } catch (SQLException e) {


        } catch (ClassNotFoundException e) {
        }
    }
}