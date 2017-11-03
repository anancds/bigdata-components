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
    System.out.println((double)f1);
    Class.forName("org.apache.hive.jdbc.HiveDriver");
    Connection conn = DriverManager.getConnection("jdbc:hive2://hbp212:10007", "root", "hik12345");
//        String sql = "select count(rowKey),dat from test_ZG03 group by dat ";
    String sql = "select * from test_ZG03 where rowKey = '888' limit 10";
    HivePreparedStatement pstat = (HivePreparedStatement) conn.prepareStatement(sql);
    HiveQueryResultSet rs = (HiveQueryResultSet) pstat.executeQuery();
    ResultSetMetaData meta = rs.getMetaData();

    while (rs.next()) {
      String str = "";
      for (int i = 1; i <= meta.getColumnCount(); i++) {
        str += meta.getColumnTypeName(i) + " ";
        str += rs.getString(i) + " ";
        if (i == 8) {
          System.out.println(rs.getFloat(i));
        }
      }
      System.out.println("\t" + str);
//            System.out.println(rs.getString(0));
//            break;
    }
//        System.out.println(rs.getTotalRows());
    pstat.close();
    conn.close();
  }

}
