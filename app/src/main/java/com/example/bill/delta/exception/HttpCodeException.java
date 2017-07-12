package com.example.bill.delta.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import retrofit2.adapter.rxjava.HttpException;


public class HttpCodeException extends RuntimeException {

  public HttpCodeException(Throwable e) {
    this(getApiExceptionMessage(e));
  }

  public HttpCodeException(String detailMessage) {
    super(detailMessage);
  }

  public static String getApiExceptionMessage(Throwable e) {
    String message = "";

     if (e instanceof HttpException) {
      HttpException httpException = (HttpException) e;
      //httpException.response().errorBody().string()
      int code = httpException.code();
      if (code == 500 || code == 404) {
        message = "服务器出错";
      }
    } else if (e instanceof ConnectException) {
      message = "网络断开,请打开网络!";
    } else if (e instanceof SocketTimeoutException) {
      message = "网络连接超时!!";
    } else {
      message = "发生未知错误";
    }
    return message;
  }
}
