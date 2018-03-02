package com.cds.jvm.jvmparam;

/**
 * -Xmx20m -Xms20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails
 */
public class NewSizeDemo {

    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++) {
            b = new byte[1024 * 1024];
        }
    }
}
