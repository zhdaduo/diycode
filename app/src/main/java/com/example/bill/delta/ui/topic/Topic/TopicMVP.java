package com.example.bill.delta.ui.topic.Topic;

import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface TopicMVP {

  interface View extends BaseView {

    void showTopic(TopicDetail topicDetail);

    void loadTopicFinish();

    void showFavorite(boolean bool);

    void showUnFavorite(boolean bool);

    void showFollow(boolean bool);

    void showUnFollow(boolean bool);

    void showLike(boolean bool);

    void showUnLike(boolean bool);

    void showSignIn();
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getTopic(int id);

    void favorite(int id);

    void unFavorite(int id);

    void follow(int id);

    void unFollow(int id);

    void like(String obj_type, Integer obj_id);

    void unLike(String obj_type, Integer obj_id);
  }
}
