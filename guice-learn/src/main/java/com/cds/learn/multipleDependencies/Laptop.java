package com.cds.learn.multipleDependencies;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:38
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:38
 * modify by reason:{方法名}:{原因}
 */
public class Laptop {

  private String model;
  private String price;

  public Laptop(){
    this.model = "HP 323233232";
    this.price = "$545034";
  }

  public String toString(){
    return "[Laptop: " + model + "," + price + "]";
  }
}
