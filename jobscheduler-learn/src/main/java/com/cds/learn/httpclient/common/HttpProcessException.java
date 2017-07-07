package com.cds.learn.httpclient.common;

public class HttpProcessException extends Exception {

  private static final long serialVersionUID = -2749168865492921426L;

  public HttpProcessException(Exception e) {
    super(e);
  }

  /**
   * @param msg 错误信息
   */
  public HttpProcessException(String msg) {
    super(msg);
  }

  /**
   * @param message 错误信息
   * @param e Exception
   */
  public HttpProcessException(String message, Exception e) {
    super(message, e);
  }

}
