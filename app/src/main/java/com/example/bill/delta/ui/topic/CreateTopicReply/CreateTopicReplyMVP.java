package com.example.bill.delta.ui.topic.CreateTopicReply;

import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface CreateTopicReplyMVP {

  interface View extends BaseView {

    void getResult(boolean isSuccessful);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void createReply(int id, String body);
  }
}
