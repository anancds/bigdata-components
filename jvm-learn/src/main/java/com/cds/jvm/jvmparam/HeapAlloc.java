package com.cds.jvm.jvmparam;

/**
 * -Xmx20m -Xms5m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC
 */
public class HeapAlloc {

    public static void main(String[] args) {
        System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() + " bytes");
        System.out.println("Free memory: " + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() + " bytes");

        byte[] b = new byte[1024 * 1024];

        System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() + " bytes");
        System.out.println("Free memory: " + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() + " bytes");

        b = new byte[4 * 1024 * 1024];

        System.out.println("Max memory: " + Runtime.getRuntime().maxMemory() + " bytes");
        System.out.println("Free memory: " + Runtime.getRuntime().freeMemory() + " bytes");
        System.out.println("Total memory: " + Runtime.getRuntime().totalMemory() + " bytes");
    }
}
