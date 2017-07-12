package com.example.bill.delta.ui.user.UserFollow;

import com.example.bill.delta.bean.user.UserInfo;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface UserFollowMVP {

  interface View extends BaseView {

    void getUserFollowing(List<UserInfo> userInfoList);

    void hideLoading();
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getUserFollowing(String loginName, Integer offset);

  }
}
