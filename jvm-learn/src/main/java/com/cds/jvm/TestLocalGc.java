package com.cds.jvm;

/**
 * -XX:+PrintGC
 */
public class TestLocalGc {

    public static void main(String[] args) {
        TestLocalGc testLocalGc = new TestLocalGc();
        //        testLocalGc.localvarGc1();
        //        testLocalGc.localvarGc2();
        //        testLocalGc.localvarGc3();
        testLocalGc.localvarGc4();
    }

    public void localvarGc1() {
        byte[] a = new byte[6 * 1024 * 1024];
        System.gc();
    }

    public void localvarGc2() {
        byte[] a = new byte[6 * 1024 * 1024];
        a = null;
        System.gc();
    }

    public void localvarGc3() {
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }
        System.gc();
    }

    public void localvarGc4() {
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }

        int c = 10;
        System.gc();
    }

    public void localvarGc5() {
        localvarGc1();
        System.gc();
    }

}
