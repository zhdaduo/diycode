package com.example.bill.delta.view.Listener;

import java.util.List;

/**
 * Created by Bill on 2017/7/8.
 */

public interface OnHttpCallBackList<T> {

  void onSuccessful(List<T> t);

  void onFaild(String errorMsg);
}
