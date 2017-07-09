package com.example.bill.delta.ui.topic.CreateTopicReply;

import android.util.Log;
import com.example.bill.delta.bean.topic.event.CreateTopicReplyEvent;
import com.example.bill.delta.ui.topic.CreateTopicReply.CreateTopicReplyMVP.Model;
import com.example.bill.delta.ui.topic.CreateTopicReply.CreateTopicReplyMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateTopicReplyPresenter implements CreateTopicReplyMVP.Presenter {

  private static final String TAG = "CreateTopicReplyPresent";

  private CreateTopicReplyMVP.Model mModel;
  private CreateTopicReplyMVP.View mView;

  public void createTopicReply(int id, String body) {
    mModel.createReply(id, body);
  }

  @Inject
  public CreateTopicReplyPresenter(
      Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void getResult(CreateTopicReplyEvent createTopicReplyEvent) {
    Log.d(TAG, "getResult");
    mView.getResult(createTopicReplyEvent.isSuccessful());
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
