package com.cds.learn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.hive.jdbc.HivePreparedStatement;
import org.apache.hive.jdbc.HiveQueryResultSet;

public class JdbcTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        float f1 = 252.99f;
        System.out.println((double) f1);
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection conn = DriverManager.getConnection("jdbc:hive2://vsp1:10007", "root", "123456");
        //        String sql = "select count(rowKey),dat from test_ZG03 group by dat ";
        for (int j = 0; j < 1; j++) {

            String sql = "select * from HIK_SMART_METADATA limit 10000";
            HivePreparedStatement pstat = (HivePreparedStatement) conn.prepareStatement(sql);
            HiveQueryResultSet rs = (HiveQueryResultSet) pstat.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();

            int count = 0;
            while (rs.next()) {
                count++;
                String str = "";
                for (int i = 1; i <= meta.getColumnCount(); i++) {

                    str += meta.getColumnTypeName(i) + " ";
                    str += rs.getString(i) + " ";
                    if (i == 8) {
                        //          System.out.println(rs.getFloat(i));
                    }
                }
                //      System.out.println("\t" + str);
                //            System.out.println(rs.getString(0));
                //            break;
                System.out.println("count: " + count);
            }
            pstat.close();
        }
        //        System.out.println(rs.getTotalRows());
        conn.close();
    }

}
