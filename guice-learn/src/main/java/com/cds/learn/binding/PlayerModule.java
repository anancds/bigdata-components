package com.cds.learn.binding;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 17:44
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 17:44
 * modify by reason:{方法名}:{原因}
 */
public class PlayerModule implements Module {

  @Override
  public void configure(Binder binder) {
    binder.bind(Player.class).annotatedWith(Good.class).to(GoodPlayer.class);
    binder.bind(Player.class).annotatedWith(Bad.class).to(BadPlayer.class);
  }
}
