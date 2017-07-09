package com.example.bill.delta.view.Listener;

/**
 * interface CallBack
 * */
public interface OnHttpCallBack<T> {

  void onSuccessful(T t);

  void onFaild(String errorMsg);
}
