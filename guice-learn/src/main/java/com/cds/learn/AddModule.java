package com.cds.learn;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 16:46
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 16:46
 * modify by reason:{方法名}:{原因}
 */
public class AddModule implements Module {

  public void configure(Binder binder) {
    binder.bind(Add.class).to(SimpleAdd.class);
  }

}
