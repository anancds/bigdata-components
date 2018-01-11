package com.cds.learn;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {

    private static void test1() {
        String options = "\"options\":\"zk '10.6.131.144',hbaseTableName 'test',indexerClass 'com.hikvision.bigdata.ude.datamanipulator.EsIndexer',indexerURL '10.6.131.144:9300:allsomtype/allsomtype'\"";
        System.out.println(options);
        Pattern tableP = Pattern.compile("hbaseTableName\\s+'([^,']+)'");
        Matcher tableM = tableP.matcher(options);
        System.out.println("hbaseTabelName: " + tableM.groupCount());
        if (tableM.find() && tableM.groupCount() == 1) {
            System.out.println("hbaseTabelName: " + tableM.group(1));
        }

        Pattern zkP = Pattern.compile("zk\\s+'([^']+)'");
        Matcher zkM = zkP.matcher(options);
        if (zkM.find()) {
            System.out.println("zk: " + zkM.group(1));
        }
    }

    private static void test2() {

        String regEx = "count(\\d+)(df)";
        String s = "count000dfdfsdffaaaa1";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(s);
        if (mat.find()) {
            System.out.println(mat.group(1));
        }
    }

    private static void test3() {
        String s = "'[(separationStartTime \"   2017-12-16 00:00:00\",separationEndTime \"2017-12-19 00:00:00\",separationTimeInterval \"day:1\")]'";
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher matcher = pattern.matcher(s);
        System.out.println(matcher.groupCount());
        while (matcher.find()) {
            System.out.println(matcher.group());
            String res = matcher.group();
            String[] temps = res.split(",");
            System.out.println(Arrays.toString(temps));
            int index1 = temps[0].indexOf("\"");
            int index2 = temps[0].lastIndexOf("\"");
            int index3 = temps[1].indexOf("\"");
            int index4 = temps[1].lastIndexOf("\"");
            int index5 = temps[2].indexOf("\"");
            int index6 = temps[2].lastIndexOf("\"");
            System.out.println(temps[0].substring(index1 +1, index2).trim());
            System.out.println(temps[1].substring(index3 +1, index4).trim());
            System.out.println(temps[2].substring(index5 +1, index6).trim());
        }
    }

    public static void main(String[] args) {

        //        test1();
        //    test2();
        test3();
    }
}
