package com.example.bill.delta.ui.user.User;

import android.util.Log;
import com.example.bill.delta.bean.user.event.MeEvent;
import com.example.bill.delta.bean.user.event.UserDetailInfoEvent;
import com.example.bill.delta.ui.user.User.UserMVP.Model;
import com.example.bill.delta.ui.user.User.UserMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserPresenter implements UserMVP.Presenter {

  private static final String TAG = "UserPresenter";

  private UserMVP.Model mModel;
  private UserMVP.View mView;

  @Inject
  public UserPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getMe(MeEvent event) {
    mView.getMe(event.getUserDetailInfo());
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void getMe(UserDetailInfoEvent userDetailInfoEvent) {
    Log.d(TAG, "getMe");
    mView.getUser(userDetailInfoEvent.getUserDetailInfo());
    EventBus.getDefault().removeStickyEvent(userDetailInfoEvent);
  }

  public void getMe() {
    mModel.getMe();
  }

  public void getUser(String loginName) {
    mModel.getUser(loginName);
  }

  @Override
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
