package com.cds.jvm.jvmheap;

public class StringTest {
    public static void main(String[] args) {
        String str1 = new String("abc");
        String str2 = new String("abc");
        System.out.println(str1 == str2);
        System.out.println(str1 == str2.intern());
        System.out.println("abc" == str2.intern());
        System.out.println(str1.intern() == str2.intern());
    }
}
