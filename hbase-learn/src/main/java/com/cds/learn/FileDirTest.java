package com.cds.learn;

import java.io.File;

public class FileDirTest {

  public static void main(String[] args) {
    File localDir = new File("D:\\");
    String[] subRes = localDir.list();
    System.out.println(subRes);
  }

}
