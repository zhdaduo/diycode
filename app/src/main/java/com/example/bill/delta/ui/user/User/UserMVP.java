package com.example.bill.delta.ui.user.User;

import com.example.bill.delta.bean.user.UserDetailInfo;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface UserMVP {

  interface View extends BaseView {

    void getMe(UserDetailInfo userDetailInfo);

    void getUser(UserDetailInfo userDetailInfo);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getMe();

    void getUser(String loginName);
  }
}
