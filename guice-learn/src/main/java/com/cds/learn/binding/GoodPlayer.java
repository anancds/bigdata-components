package com.cds.learn.binding;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:43
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:43
 * modify by reason:{方法名}:{原因}
 */
public class GoodPlayer implements Player{

  public void bat() {
    System.out.println("I can hit any ball");
  }

  public void bowl() {
    System.out.println("I can also bowl");
  }
}
