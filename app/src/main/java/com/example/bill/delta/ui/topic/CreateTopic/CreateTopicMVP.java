package com.example.bill.delta.ui.topic.CreateTopic;

import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface CreateTopicMVP {

  interface View extends BaseView {

    void getNewTopic(TopicDetail topicDetail);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void newTopic(final String title, String body, int nodeId);
  }
}
