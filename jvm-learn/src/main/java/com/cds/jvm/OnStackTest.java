package com.cds.jvm;

/**
 * -server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-UseTLAB -XX:+EliminateAllocations
 */
public class OnStackTest {

    private static void alloc() {
        User u = new User();
        u.id = 5;
        u.name = "cds";
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            alloc();
        }

        long e = System.currentTimeMillis();
        System.out.println(e - b);

    }

    public static class User {
        int id = 0;
        String name = "";
    }
}
