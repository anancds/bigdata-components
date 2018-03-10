package com.cds.jvm.jvmheap;

import com.cds.jvm.cglib.CglibBean;

import java.util.HashMap;

/**
 * -XX:MaxMetaspaceSize=5m
 */
public class PermOOM {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10000; i++) {
                CglibBean bean = new CglibBean(new HashMap());

            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}
