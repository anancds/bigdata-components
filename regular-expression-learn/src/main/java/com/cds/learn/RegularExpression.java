package com.cds.learn;

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
        String s = "( a b), (fsd)";
        Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
        Matcher matcher = pattern.matcher(s);
        System.out.println(matcher.groupCount());
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    public static void main(String[] args) {

//        test1();
        //    test2();
        test3();
    }
}
