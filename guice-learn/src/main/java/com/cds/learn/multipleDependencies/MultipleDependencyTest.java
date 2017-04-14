package com.cds.learn.multipleDependencies;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:39
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:39
 * modify by reason:{方法名}:{原因}
 */
public class MultipleDependencyTest {

  public static void main(String[] args) {

    Injector injector = Guice.createInjector();
    Person person = injector.getInstance(Person.class);
    person.diplayInfo();
  }
}
