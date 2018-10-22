package com.ithawk.sqldemo.dataBaseConnect;

import java.sql.*;
import java.util.Properties;

/**
 * 描述: vertica数据库连接
 *
 * @author IThawk
 * @create 2018-10-16 15:54
 */
public class ConnectionVertica {
    public static void main(String[] args) {
        /* JDBC 3.0 以前的版本需要添加下边这段代码 */
/* 
         * try { Class.forName("com.vertica.jdbc.Driver"); } catch 
         * (ClassNotFoundException e) { // Could not find the driver class. 
         * Likely an issue // with finding the .jar file. 
         * System.err.println("Could not find the JDBC driver class."); 
         * e.printStackTrace(); return; // Bail out. We cannot do anything 
         * further. } 
         */
/***
 * 用于连接数据库的配置信息
 */
        Properties myProp = new Properties();
        //用于设置数据库的用户名
        myProp.put("user", "dbadmin");
        //用于设置数据库的密码
        myProp.put("password", "Ceshi123");


        //Set streamingBatchInsert to True to enable streaming mode for batch inserts.
        //myProp.put("streamingBatchInsert", "True");
//l连接数据库，连接
        Connection conn;
        try {
            //设置连接信息，这里包括IP,端口，数据库名称
            conn = DriverManager.getConnection("jdbc:vertica://172.19.1.135:5433/DB_vertica", myProp);
            // establish connection and make a table for the data.
            //创建连接的流
            Statement stmt = conn.createStatement();

            // Print the resulting table.
            ResultSet rs = null;
            rs = stmt.executeQuery("SELECT max(END_TIME),sum(AVERAGE_MEMORY_USAGE_PERCENT),sum(AVERAGE_CPU_USAGE_PERCENT)FROM v_monitor.system_resource_usage group by NODE_NAME ");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " - 1 *"
                        + rs.getString(2).trim() + " -2 *"
                        + rs.getString(3).trim() + " -3 *"

                       );
            }
            System.out.println("-----------------------------------------------------------------");
//            rs = stmt.executeQuery("SELECT '0','healthyRate', TRIM (TO_CHAR (ROUND ((LAST_GOOD_EPOCH /(SELECT CURRENT_EPOCH FROM system)),2),'999999999999999999.00')) ,'@*','?' FROM system;" );
            String sql ="SELECT '0','memoryUsedRate',TRIM(TO_CHAR (ROUND ((a.a/ b.b),2),'999999999999999999.00') ),'@*','?'  FROM (SELECT sum(AVERAGE_MEMORY_USAGE_PERCENT) as a FROM v_monitor.system_resource_usage GROUP BY end_time ORDER BY end_time DESC limit 1) AS a,(SELECT count(*) AS b FROM nodes) AS b;";
            sql= sql.replace("?","1");
            rs = stmt.executeQuery(sql );

            while (rs.next()) {
                System.out.println(rs.getString(1) + " - 1 *   "
                        + rs.getString(2).trim() + " -2 *   "
                        + rs.getString(3).trim() + " -3 *   "
                        + rs.getString(4).trim() + " -4 *  "
                        + rs.getString(5).trim() + " -5 *  "
//                        + rs.getString(6).trim() + " -6 *  "
//                        + rs.getString(7).trim() + " -7 *  "
//                        + rs.getString(8).trim() + " -8 *  "
                );
            }
            // Cleanup
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
