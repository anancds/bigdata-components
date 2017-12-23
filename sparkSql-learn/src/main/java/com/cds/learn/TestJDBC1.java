package com.cds.learn;

import org.apache.hive.jdbc.HivePreparedStatement;
import org.apache.hive.jdbc.HiveQueryResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;

/**
 */
public class TestJDBC1 {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        HivePreparedStatement pstat = null;
        HiveQueryResultSet rs = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn = DriverManager.getConnection("jdbc:hive2://10.17.148.131:10007", "root", "123456");

            int inNums = 4000;
            StringBuilder stringBuilderIn = new StringBuilder("");
            for (int k = 0; k < inNums; k++) {
                if (0 == k && k == inNums - 1) {
                    stringBuilderIn
                            .append("select * from BAYONET_VEHICLEPASS   where 1=1 and ((vehiclesign in (" + String.valueOf(k) + "))");
                } else if (0 == k) {
                    stringBuilderIn
                            .append("select * from BAYONET_VEHICLEPASS   where 1=1 and ((vehiclesign in (" + String.valueOf(k) + ",");
                } else {
                    if (0 == k % 100) {
                        stringBuilderIn.delete(stringBuilderIn.lastIndexOf(","), stringBuilderIn.lastIndexOf(",") + 1);
                        stringBuilderIn.append(") or vehiclesign in (");
                    } else if (inNums - 1 == k) {
                        stringBuilderIn.append(String.valueOf(k) + "))");
                    } else {
                        stringBuilderIn.append(String.valueOf(k) + ",");
                    }
                }
            }
            /*String sqlIn = stringBuilderIn.toString()
                    + " and ((ts < 1610159612801 and ts > 1310159312801) " +
                    " or (ts < 1510159440001 and ts > 1510139440001) " +
                    " or (ts < 1510155106532 and ts > 1510055106532) " +
                    " or (ts < 1610155954410 and ts > 1410151954410) " +
                    " or (ts < 1510156776110 and ts > 1510150776110)) " +
                    " )"
                    + " order by ts desc limit 20";*/

            String sqlIn = stringBuilderIn.toString() + ") order by ts desc limit 20";

            // or
            StringBuilder stringBuilderOr = new StringBuilder("");
            for (int i = 0; i < inNums; i++) {
                stringBuilderOr.append("inte = " + String.valueOf(i) + " or ");
            }
            String sqlOr = "select * from testTable where 1 = 1 and ((" + stringBuilderOr
                    .substring(0, stringBuilderOr.length() - 3) + ") and ((ts < 1610159612801 and ts > 1310159312801) "
                    + " or (ts < 1510159440001 and ts > 1510139440001) "
                    + " or (ts < 1510155106532 and ts > 1510055106532) "
                    + " or (ts < 1610155954410 and ts > 1410151954410) "
                    + " or (ts < 1510156776110 and ts > 1510150776110)) " + " )" + " order by ts desc limit 20";

            //sql
            //            System.out.println(sqlOr);
            long startTime = System.currentTimeMillis();
            System.out.println(sqlIn);
            pstat = (HivePreparedStatement) conn.prepareStatement(sqlIn);
            rs = (HiveQueryResultSet) pstat.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            System.out.println("SQL Consumed times " + (System.currentTimeMillis() - startTime) + " ms");

            int j = 0;
            while (rs.next()) {
                String str = "";
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    // System.out.println("\t" + str);
                    //System.out.println("Nums: " + j++ + " " + rs.getString(i));
                }
                //System.out.println(rs.getString(4));
                //break;
            }
            //System.out.println(rs.getTotalRows());

        } finally {
            pstat.close();
            conn.close();
        }

    }
}
