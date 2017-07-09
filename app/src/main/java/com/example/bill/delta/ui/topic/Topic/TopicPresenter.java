package com.example.bill.delta.ui.topic.Topic;


import android.util.Log;
import com.example.bill.delta.bean.topic.event.CreateTopicEvent;
import com.example.bill.delta.bean.topic.event.FavoriteEvent;
import com.example.bill.delta.bean.topic.event.FollowEvent;
import com.example.bill.delta.bean.topic.event.LoadTopicDetailFinishEvent;
import com.example.bill.delta.bean.topic.event.SignInEvent;
import com.example.bill.delta.bean.topic.event.TopicDetailEvent;
import com.example.bill.delta.bean.topic.event.UnFavoriteEvent;
import com.example.bill.delta.bean.topic.event.UnFollowEvent;
import com.example.bill.delta.ui.topic.Topic.TopicMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TopicPresenter implements TopicMVP.Presenter{

  private static final String TAG = "TopicPresenter";
  private static final String LIKE_OBJ_TYPE_TOPIC = "topic";

  private TopicMVP.Model mModel;
  private TopicMVP.View mView;

  @Inject
  public TopicPresenter(TopicMVP.Model mModel) {
    this.mModel = mModel;
  }

  public void getTopic(int id) {
    mModel.getTopic(id);
  }

  public void favoriteTopic(int id) {
    mModel.favorite(id);
  }

  public void unFavoriteTopic(int id) {
    mModel.unFavorite(id);
  }

  public void followTopic(int id) {
    mModel.follow(id);
  }

  public void unFollowTopic(int id) {
    mModel.unFollow(id);
  }

  public void like(Integer id) {
    mModel.like(LIKE_OBJ_TYPE_TOPIC, id);
  }

  public void unLike(Integer id) {
    mModel.unLike(LIKE_OBJ_TYPE_TOPIC, id);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void showTopic(TopicDetailEvent topicDetailEvent) {
    Log.d(TAG, "showTopic");
    mView.showTopic(topicDetailEvent.getTopicDetail());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void loadTopicFinish(LoadTopicDetailFinishEvent event) {
    Log.d(TAG, "loadTopicFinish");
    mView.loadTopicFinish();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void showNewTopic(CreateTopicEvent createTopicEvent) {
    Log.d(TAG, "showNewTopic");
    mView.showTopic(createTopicEvent.getTopicDetail());
    EventBus.getDefault().removeStickyEvent(createTopicEvent);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showFavorite(FavoriteEvent event) {
    mView.showFavorite(event.isResult());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showUnFavorite(UnFavoriteEvent event) {
    mView.showUnFavorite(event.isResult());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showFollow(FollowEvent event) {
    mView.showFollow(event.isResult());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showUnFollow(UnFollowEvent event) {
    mView.showUnFollow(event.isResult());
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showSignIn(SignInEvent event) {
    mView.showSignIn();
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
