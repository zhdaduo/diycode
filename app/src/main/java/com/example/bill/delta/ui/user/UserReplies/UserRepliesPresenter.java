package com.example.bill.delta.ui.user.UserReplies;

import android.util.Log;
import com.example.bill.delta.bean.topic.event.RepliesEvent;
import com.example.bill.delta.ui.user.UserReplies.UserRepliesMVP.Model;
import com.example.bill.delta.ui.user.UserReplies.UserRepliesMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserRepliesPresenter implements UserRepliesMVP.Presenter {

  private static final String TAG = "RepliesPresenter";

  private UserRepliesMVP.Model mModel;
  private UserRepliesMVP.View mView;

  @Inject
  public UserRepliesPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void showReplies(RepliesEvent event) {
    Log.d(TAG, "showReplies");
    mView.showReplies(event.getReplyList());
    EventBus.getDefault().removeStickyEvent(event);
  }

  public void getReplies(String loginName, Integer offset) {
    mModel.getUserReplies(loginName, offset, null);
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
