package com.cds.learn.player;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:35
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:35
 * modify by reason:{方法名}:{原因}
 */
public class PlayerTest {

  public static void main(String[] args) {

    Injector injector = Guice.createInjector();
    Player player = injector.getInstance(Player.class);
    player.name = "David Boon";
    System.out.println(player);
  }
}
