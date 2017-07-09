package com.example.bill.delta.ui.topic.CreateTopic;

import com.example.bill.delta.bean.topic.event.CreateTopicEvent;
import com.example.bill.delta.ui.topic.CreateTopic.CreateTopicMVP.Model;
import com.example.bill.delta.ui.topic.CreateTopic.CreateTopicMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateTopicPresenter implements CreateTopicMVP.Presenter {

  private static final String TAG = "CreateTopicPresenter";

  private CreateTopicMVP.Model mModel;
  private CreateTopicMVP.View mView;

  @Inject
  public CreateTopicPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void getNewTopic(CreateTopicEvent createTopicEvent) {
    mView.getNewTopic(createTopicEvent.getTopicDetail());
  }

  public void newTopic(String title, String body, int node_id) {
    mModel.newTopic(title, body, node_id);
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
