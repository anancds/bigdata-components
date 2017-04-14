package com.cds.learn;

import com.google.inject.ImplementedBy;

/**
 * <p></p>
 * author chendongsheng5 2017/4/14 16:45
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/4/14 16:45
 * modify by reason:{方法名}:{原因}
 */
@ImplementedBy(SimpleAdd.class)
public interface Add {

  public int add(int a, int b);

}
