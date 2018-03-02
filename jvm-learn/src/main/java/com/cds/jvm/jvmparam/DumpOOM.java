package com.cds.jvm.jvmparam;

import java.util.Vector;

/**
 * -Xmx20m -Xms5m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=a.dump
 */
public class DumpOOM {

    public static void main(String[] args) {
        Vector v = new Vector();
        for (int i = 0; i < 25; i++) {
            v.add(new byte[1024 * 1024]);
        }
    }
}
