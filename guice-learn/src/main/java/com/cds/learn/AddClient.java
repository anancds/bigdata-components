package com.cds.learn;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 16:47
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 16:47
 * modify by reason:{方法名}:{原因}
 */
public class AddClient {

  public static void main(String[] args) {

    Injector injector = Guice.createInjector(new AddModule());
    Add add = injector.getInstance(Add.class);
    System.out.println(add.add(10, 54));
  }

}
