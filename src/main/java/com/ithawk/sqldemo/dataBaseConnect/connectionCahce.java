package com.ithawk.sqldemo.dataBaseConnect;
import java.sql.*;
import java.util.stream.Stream;

public class connectionCahce {
    // 定义需要使用的常量
    private static final String DRIVER = "com.intersys.jdbc.CacheDriver";
    private static final String URL = "jdbc:Cache://localhost:1972/%SYS";
    private static final String USER = "_SYSTEM";
    private static final String PASSWORD = "123";

    private static Connection conn;

    static {
        // 1,加载驱动类
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动注册失败~");
            e.printStackTrace();
        }
    }
    // 获取连接对象的方法
    public static synchronized Connection getConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }


    // 关闭资源的方法
    public static void closeConn(ResultSet rs, Statement stmt, Connection conn){
        try {
            if(rs!=null){
                rs.close();
            }
            if(stmt!=null){
                stmt.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       Connection connection=getConn();
        Statement stream= null;
        try {
            stream = connection.createStatement();
            ResultSet set= stream.executeQuery("SELECT sum(MemoryUsed) FROM  SYS.Process  ");
            while(set.next()){
                System.out.println(set.getString(1));
                //System.out.println(set.getString(2));
                //System.out.println(set.getString(3));
               // System.out.println("\r\t");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("------------");
    }
}
