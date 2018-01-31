package com.cds.learn;

import java.util.ArrayList;
import java.util.List;

public class CopyTest {

    private static void test(List<Byte> a , List<Byte> b) {
        a.add((byte)65);
        for (Byte temp: a) {
            System.out.println(temp.toString());
        }
    }
    public static void main(String[] args) {
        List<Byte> startA = new ArrayList<>();
        List<Byte> endA = new ArrayList<>();
        test(startA, endA);
        System.out.println(startA);

    }
}
