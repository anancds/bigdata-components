package com.cds.learn;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PostgresqlTest {

    private static Connection conn = null;

    public static Connection getConn()
    {
        if(conn == null)
        {
            try {
                Class.forName("org.postgresql.Driver");
//                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:postgresql://10.17.139.41:5432/mpp-test";
//                String url = "jdbc:oracle:thin:@//10.33.25.25:1521/orcl";
                conn = DriverManager.getConnection(url, "gpadmin", "gpadmin");
//                conn = DriverManager.getConnection(url, "BMS_HUZHOU_5Y", "hik12345");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException, ParseException {


        Connection connection = getConn();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PreparedStatement pstat = null;
//        pstat = connection.prepareStatement("INSERT INTO \"public\".\"BMS_VEHICLE_PASS\" VALUES ('5990102565', '1', '2370', '-1', '9', '2017-04-02 21:19:09.000000 +08:00:00', 'http://33.157.10.23:8040/pus/pic.do?act=showPic&type=1&url=ftp%3A%2F%2Fcdt%3Aftpadmin%4033.157.10.12%2F2017-04-02T21%2F330522000000670030000001020002%2F03%2F2%2F3305220000915590553_1.jpg', 'http://33.157.10.23:8040/pus/pic.do?act=showPic&type=1&url=ftp%3A%2F%2Fcdt%3Aftpadmin%4033.157.10.12%2Flic%2F2017-04-02T21%2F330522000000670030000001020002%2F03%2F2%2F3305220000915590553_6.jpg', 'http://33.157.10.23:8040/pus/pic.do?act=showPic&type=1&url=ftp%3A%2F%2Fcdt%3Aftpadmin%4033.157.10.12%2F2017-04-02T21%2F330522000000670030000001020002%2F03%2F2%2F3305220000915590553_1.jpg', '2', '浙E0985E', '1', '2017-04-02 21:20:39.000000 +08:00:00', '0', '0', '0', '1', '2', NULL, '0', '0', '0', '9,3602')");
        pstat = connection.prepareStatement("INSERT INTO \"public\".\"BMS_VEHICLE_PASS\" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        pstat.setObject(1,"4343");
        pstat.setObject(2,"0" );
        pstat.setObject(3,"0" );
        pstat.setObject(4,"0" );
        pstat.setObject(5,"0" );
        pstat.setObject(6,new Timestamp(format.parse("2017-04-03 09:08:20.000000 +08:00:00").getTime()) );
        pstat.setObject(7,"0" );
        pstat.setObject(8,"0" );
        pstat.setObject(9,"0" );
        pstat.setObject(10,"0" );
        pstat.setObject(11,"0" );
        pstat.setObject(12,"0" );
        pstat.setObject(13,new Timestamp(format.parse("2017-04-03 09:08:20.000000 +08:00:00").getTime()) );
        pstat.setObject(14,"0" );
        pstat.setObject(15,"0" );
        pstat.setObject(16,"0" );
        pstat.setObject(17,"0" );
        pstat.setObject(18,"0" );
        pstat.setObject(19,"0" );
        pstat.setObject(20,"0" );
        pstat.setObject(21,"0" );
        pstat.setObject(22,"0" );
        pstat.setObject(23,"9,24002");
        pstat.addBatch();
        pstat.executeBatch();


//        ResultSet rs = pstat.executeQuery();

    }

}
