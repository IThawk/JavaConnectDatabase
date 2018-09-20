package com.ithawk.sqldemo.dataBaseConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectSqlserver {


    public void testSqlserver() {

        //实现对数据库的访问
        Connection conn = null;
        ArrayList students = new ArrayList();//定义与初始化ArrayList数组，相当于定义数组，但是容量比数组大
        StringBuffer str = new StringBuffer();
        try {
            //获取连接
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动
            String dbURL = "jdbc:sqlserver://172.23.1.231:1433;database=master";  //连接服务器和数据库sample
            //运行SQL语句
            String userName = "sa";  //默认用户名
            String userPwd = "Ceshi123";
            Class.forName(driverName);
            conn = DriverManager.getConnection(dbURL, userName, userPwd);
            Connection conn1 = DriverManager.getConnection(dbURL, userName, userPwd);

            Connection conn2 = DriverManager.getConnection(dbURL, userName, userPwd);

            if (conn != null) {
                System.out.println("Connection Successful!");  //如果连接成功 控制台输出
            } else {

                System.out.println("Connection fail!");

            }
            while (1 == 1) {
                // 密码
                Connection conn3 = DriverManager.getConnection(dbURL, userName, userPwd);
                String sql = "SELECT status,COUNT(status) FROM master..sysprocesses GROUP BY status;";//SQL语句，选择数据表student中的所有数据
                Statement stat = conn.createStatement();
                ResultSet rs1 = stat.executeQuery(sql);
                ResultSet rs = stat.executeQuery(sql);//定义ResultSet类，用于接收获取的数据

                while (rs.next()) {
                    //实例化VO
                    System.out.print(rs.getString(1));
                    System.out.println(rs.getString(2));
                }

                // rs.close();
                //stat.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }/* finally {
                try {//关闭连接
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                } catch (Exception ex) {
                }

            }*/
    }
}
