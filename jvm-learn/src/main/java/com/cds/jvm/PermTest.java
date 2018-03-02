package com.cds.jvm;

/**
 * -XX:+PrintVMOptions -XX:+PrintCommandLineFlags -XX:+PrintFlagsFinal
 */
public class PermTest {

    public static void main(String[] args) {
        int i = 0;
        try {
            for (i = 0; i < 100000; i++) {
//               CglibBean bean = new CglibBean("cds" + i, new HashMap<>());
            }
        } catch (Exception e) {
            System.out.println("total create:" + i);
        }

    }
}
