package com.example.bill.delta.ui.user.UserFollow;

import com.example.bill.delta.bean.user.event.UserFollowingEvent;
import com.example.bill.delta.ui.user.UserFollow.UserFollowMVP.Model;
import com.example.bill.delta.ui.user.UserFollow.UserFollowMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserFollowPresenter implements UserFollowMVP.Presenter {

  private UserFollowMVP.Model mModel;
  private UserFollowMVP.View mView;

  @Inject
  public UserFollowPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getUserFollowing(UserFollowingEvent event) {
    mView.getUserFollowing(event.getUserInfoList());
  }

  public void getUserFollowing(String loginName, Integer offset) {
    mModel.getUserFollowing(loginName, offset);
  }

  public void bind(View view) {
    this.mView = view;
  }

  @Override
  public void unbind() {
    this.mView = null;
  }

  @Override
  public void start() {
    EventBus.getDefault().register(this);
  }

  @Override
  public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
