package com.example.bill.delta.ui.user.UserTopics;

import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.View;

public interface UserTopicsMVP {

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getUserCreateTopics(String loginName, Integer offset, Integer limit);

    void getUserFavoriteTopics(String loginName, Integer offset, Integer limit);
  }
}
