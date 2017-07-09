package com.example.bill.delta.ui.topic.Topics;

import android.util.Log;
import com.example.bill.delta.bean.topic.event.TopTopicsEvent;
import com.example.bill.delta.bean.topic.event.TopicsEvent;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.Model;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicsPresenter implements TopicsMVP.Presenter {

  private static final String TAG = "TopicsPresenter";

  private TopicsMVP.Model mModel;
  private TopicsMVP.View mView;

  @Inject
  public TopicsPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showTopics(TopicsEvent topicsEvent) {
    Log.d(TAG, "showTopics: " + topicsEvent.getTopicList());

    mView.showTopics(topicsEvent.getTopicList());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showTopTopics(TopTopicsEvent topicsEvent) {
    Log.d(TAG, "showTopTopics: " + topicsEvent.getTopicList());
    mView.showTopTopics(topicsEvent.getTopicList());
  }

  public void getTopics(Integer offset) {
    Log.d(TAG, "getTopics: offset: " + offset);
    mModel.getTopics(offset);
  }

  public void getTopTopics() {
    Log.d(TAG, "getTopTopics");
    mModel.getTopTopics();
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
