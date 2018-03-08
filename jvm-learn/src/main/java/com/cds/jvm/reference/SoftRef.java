package com.cds.jvm.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * -Xmx10m
 *
 * 这个问题并没有复现出来，还需要再看看
 */
public class SoftRef {

    public static void main(String[] args) {

        User u = new User(1, "cds");
        WeakReference<User> userSoftReference = new WeakReference<>(u);
//        SoftReference<User> userSoftReference = new SoftReference<>(u);
        u = null;

        System.out.println(userSoftReference.get());
        System.gc();
        System.out.println("after gc");
        System.out.println(userSoftReference.get());
//        byte[] b = new byte[1024 * 1024 * 6];
//        byte[] b = new byte[1024 * 950 * 6];
//        System.gc();
        System.out.println(userSoftReference.get());

    }

    public static class User {

        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }
}
