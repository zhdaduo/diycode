package com.example.bill.delta.ui.topic.TopicReplies;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.bill.delta.bean.topic.event.CreateTopicReplyEvent;
import com.example.bill.delta.bean.topic.event.TopicRepliesEvent;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesMVP.Model;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicRepliesPresenter implements TopicRepliesMVP.Presenter{

  private static final String TAG = "TopicRepliesPresenter";
  private int id;
  private int status;

  private TopicRepliesMVP.Model mModel;
  private TopicRepliesMVP.View mView;

  @Inject
  public TopicRepliesPresenter(
      Model mModel) {
    this.mModel = mModel;
  }

  public void setId(@NonNull int id) {
    this.id = id;
  }

  public void getReplies() {
    Log.d(TAG, "getReplies");
    if (status == 0) {
      status = 1;
      mModel.getReplies(id, null, null);
    }
  }

  public void addReplies(Integer offset) {
    Log.d(TAG, "addReplies");
    if (status == 0) {
      status = 2;
      mModel.getReplies(id, offset, null);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showReplies(TopicRepliesEvent topicRepliesEvent) {
    Log.d(TAG, "showReplies");
    if (status == 1) {
      status = 0;
      mView.showReplies(topicRepliesEvent.getTopicReplyList());
    } else if (status == 2) {
      status = 0;
      mView.addReplies(topicRepliesEvent.getTopicReplyList());
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void getNewTopicReply(CreateTopicReplyEvent createTopicReplyEvent) {
    Log.d(TAG, "getNewTopicReply");
    if (createTopicReplyEvent.isSuccessful()) {
      mView.showNewReply();
    }
    EventBus.getDefault().removeStickyEvent(createTopicReplyEvent);
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
