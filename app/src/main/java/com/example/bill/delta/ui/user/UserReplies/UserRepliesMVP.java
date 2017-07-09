package com.example.bill.delta.ui.user.UserReplies;

import com.example.bill.delta.bean.topic.Reply;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface UserRepliesMVP {

  interface View extends BaseView {

    void showReplies(List<Reply> replyList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getUserReplies(String loginName, Integer offset, Integer limit);
  }
}
