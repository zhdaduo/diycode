package com.example.bill.delta.ui.user.SignIn;

import com.example.bill.delta.bean.user.Token;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface SignInMVP {

  interface View extends BaseView {

    void getToken(Token token);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getToken(String username, String password);
  }
}
