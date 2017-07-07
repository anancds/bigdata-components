package com.cds.learn.httpclient.common;

import java.io.OutputStream;

public interface IHandler {

  /**
   * 处理异常时，执行该方法
   */
  Object failed(Exception e);

  /**
   * 处理正常时，执行该方法
   */
  Object completed(String respBody);

  /**
   * 处理正常时，执行该方法
   */
  Object down(OutputStream out);

  /**
   * 处理取消时，执行该方法
   */
  Object cancelled();
}
