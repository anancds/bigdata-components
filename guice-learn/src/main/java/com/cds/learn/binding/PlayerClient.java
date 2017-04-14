package com.cds.learn.binding;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:46
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:46
 * modify by reason:{方法名}:{原因}
 */
public class PlayerClient {

  public static void main(String[] args) {
    PlayerModule module = new PlayerModule();
    Injector injector = Guice.createInjector(new Module[]{module});
    @Good Player player = (Player) injector.getInstance(Player.class);
    player.bat();
    player.bowl();
  }

}
