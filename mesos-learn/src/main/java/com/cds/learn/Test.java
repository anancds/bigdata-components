package com.cds.learn;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import sun.nio.ch.DirectBuffer;

/**
 * <p></p>
 * author chendongsheng5 2017/5/15 18:43
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/5/15 18:43
 * modify by reason:{方法名}:{原因}
 */
public class Test {

  private static int index = 0;

  private List<byte[]> list = new ArrayList<byte[]>();
  private List<ByteBuffer> byteBufferList = new ArrayList<ByteBuffer>();

  public static void clean(final ByteBuffer byteBuffer) {
    if (byteBuffer.isDirect()) {
      ((DirectBuffer) byteBuffer).cleaner().clean();
    }
  }


  private void nonHeap(int count) throws InterruptedException {
    for (int i = 0; i < count; i++) {
      index++;
      ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024);
      byteBufferList.add(buffer);
      System.out.println(index);
      System.out.println(
          "memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (
              1024 * 1024) + "M");
      Thread.sleep(1000);
    }
    System.out.println(byteBufferList);
  }

  public static void main(String[] args) throws InterruptedException {

    Thread shutdownThread = new Thread() {
      public void run() {
        System.out.println("shutdownThread...");
      }
    };

    Runtime.getRuntime().addShutdownHook(shutdownThread);

    try {
      if (Integer.valueOf(args[0]) == 0) {
        Test test = new Test();
        test.malloc(Integer.valueOf(args[1]));
        test.print();
      } else {
        Test test = new Test();
        test.nonHeap(Integer.valueOf(args[1]));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void malloc(int count) throws InterruptedException {
    for (int i = 0; i < count; i++) {
      index++;
      byte[] a = new byte[1024 * 1024];
      list.add(a);
      System.out.println(index);
      System.out.println(
          "memory: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (
              1024 * 1024) + "M");
      Thread.sleep(1000);
    }
  }

  private void print() {
    System.out.println(list.size());
  }

}
