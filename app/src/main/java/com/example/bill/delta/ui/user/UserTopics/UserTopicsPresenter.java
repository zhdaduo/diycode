package com.example.bill.delta.ui.user.UserTopics;

import android.util.Log;
import com.example.bill.delta.bean.user.event.UserFavoriteTopicsEvent;
import com.example.bill.delta.bean.user.event.UserTopicsEvent;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.View;
import com.example.bill.delta.ui.user.UserTopics.UserTopicsMVP.Model;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UserTopicsPresenter implements UserTopicsMVP.Presenter {

  private static final String TAG = "UserTopicsPresenter";

  private UserTopicsMVP.Model mModel;
  private TopicsMVP.View mView;

  @Inject
  public UserTopicsPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showUserCreateTopics(UserTopicsEvent userTopicsEvent) {
    Log.d(TAG, "showUserCreateTopics: " + userTopicsEvent.getTopicList().size());
    if (userTopicsEvent.getTopicList().size() != 0) {
      mView.hideLoading();
      mView.showTopics(userTopicsEvent.getTopicList());
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showUserFavoriteTopics(UserFavoriteTopicsEvent event) {
    Log.d(TAG, "showUserFavoriteTopics: " + event.getTopicList().size());
    mView.hideLoading();
    mView.showTopics(event.getTopicList());
  }

  public void getUserCreateTopics(String loginName, Integer offset) {
    mModel.getUserCreateTopics(loginName, offset, null);
  }

  public void getUserFavoriteTopics(String loginName, Integer offset) {
    mModel.getUserFavoriteTopics(loginName, offset, null);
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
