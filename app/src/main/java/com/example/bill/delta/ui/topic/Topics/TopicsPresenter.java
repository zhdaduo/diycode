package com.example.bill.delta.ui.topic.Topics;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.bill.delta.bean.topic.Topic;
//import com.example.bill.delta.exception.HttpCodeException;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.Model;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.View;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TopicsPresenter implements TopicsMVP.Presenter {

  private static final String TAG = "TopicsPresenter";

  @NonNull
  private CompositeSubscription mSubscriptions;

  private TopicsMVP.Model mModel;
  private TopicsMVP.View mView;

  @Inject
  public TopicsPresenter(Model mModel) {
    this.mModel = mModel;
    mSubscriptions = new CompositeSubscription();
  }

  public void getTopics(Integer offset) {
    Subscription subscription = mModel.getTopicsObservable(offset)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<Topic>>() {
          @Override
          public void onCompleted() {
            mView.hideLoading();
          }

          @Override
          public void onError(Throwable e) {
            //mView.showRetry(HttpCodeException.getApiExceptionMessage(e));
          }

          @Override
          public void onNext(List<Topic> topics) {
            mView.showTopics(topics);
            Log.d(TAG, "showTopics: " + topics);
          }
        });
    mSubscriptions.add(subscription);
  }

  public void getTopTopics() {
    Subscription subscription = mModel.getTopTopicsObservable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<Topic>>() {
          @Override
          public void onCompleted() {
            mView.hideLoading();
          }

          @Override
          public void onError(Throwable e) {
            //mView.showRetry(HttpCodeException.getApiExceptionMessage(e));
          }

          @Override
          public void onNext(List<Topic> topics) {
            mView.showTopTopics(topics);
            Log.d(TAG, "showTopTopics: " + topics);
          }
        });
    mSubscriptions.add(subscription);
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
  public void start() {}

  @Override
  public void stop() {
    mSubscriptions.clear();
  }
}
