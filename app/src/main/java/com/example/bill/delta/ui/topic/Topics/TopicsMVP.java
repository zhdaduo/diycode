package com.example.bill.delta.ui.topic.Topics;

import com.example.bill.delta.bean.topic.Topic;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;
import rx.Observable;

public interface TopicsMVP {

  interface View extends BaseView {

    void hideLoading();

    void showRetry(String msg);

    void showTopics(List<Topic> topicList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    Observable<List<Topic>> getTopicsObservable(Integer offset);

    Observable<List<Topic>> getTopTopicsObservable();
  }
}
