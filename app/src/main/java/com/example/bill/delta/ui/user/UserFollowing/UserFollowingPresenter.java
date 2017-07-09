package com.example.bill.delta.ui.user.UserFollowing;

import com.example.bill.delta.bean.user.event.UserFollowEvent;
import com.example.bill.delta.bean.user.event.UserUnFollowEvent;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingMVP.Model;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserFollowingPresenter implements UserFollowingMVP.Presenter {

  private static final String TAG = "UserFollowingPresenter";

  private UserFollowingMVP.Model mModel;
  private UserFollowingMVP.View mView;

  @Inject
  public UserFollowingPresenter(
      Model mModel) {
    this.mModel = mModel;
  }

  public void followTopic(String loginName) {
    mModel.Follow(loginName);
  }

  public void unFollowTopic(String loginName) {
    mModel.unFollow(loginName);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showFollow(UserFollowEvent event) {
    mView.showFollow(event.getUserFollow());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showUnFollow(UserUnFollowEvent event) {
    mView.showUnFollow(event.getUserUnFollow());
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
