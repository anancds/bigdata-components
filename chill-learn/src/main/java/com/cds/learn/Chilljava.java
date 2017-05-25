package com.cds.learn;

import com.twitter.chill.KryoInstantiator;
import com.twitter.chill.KryoPool;

/**
 * Created by chendongsheng5 on 2017/5/25.
 */
public class Chilljava {

  public static void main(String[] args) {
    Object myObj = new Object();
    byte[] bytes = new byte[100];
    int POOL_SIZE = 10;
    KryoPool kryo = KryoPool.withByteArrayOutputStream(POOL_SIZE, new KryoInstantiator());
    byte[] ser = kryo.toBytesWithClass(myObj);
    Object deserObj = kryo.fromBytes(bytes);
  }

}
