package com.cds.learn.multipleDependencies;

import com.google.inject.Inject;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:38
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:38
 * modify by reason:{方法名}:{原因}
 */
public class Person {

  private Mobile mobile;
  private Laptop laptop;

  @Inject
  public Person(Mobile mobile, Laptop laptop){
    this.mobile = mobile;
    this.laptop = laptop;
  }

  public void diplayInfo(){
    System.out.println("Mobile:" + mobile);
    System.out.println("Laptop:" + laptop);
  }
}
