package com.example.bill.delta.ui.topic.TopicReplies;

import com.example.bill.delta.bean.topic.TopicReply;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface TopicRepliesMVP {

  interface View extends BaseView {

    void showReplies(List<TopicReply> topicReplyList);

    void addReplies(List<TopicReply> topicReplyList);

    void showNewReply();
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getReplies(int id, Integer offset, Integer limit);
  }
}
