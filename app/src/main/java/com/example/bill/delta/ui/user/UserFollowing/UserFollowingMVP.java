package com.example.bill.delta.ui.user.UserFollowing;

import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface UserFollowingMVP {

  interface View extends BaseView {
    void showFollow(boolean bool);

    void showUnFollow(boolean bool);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {
    void Follow(String loginName);

    void unFollow(String loginName);
  }
}
