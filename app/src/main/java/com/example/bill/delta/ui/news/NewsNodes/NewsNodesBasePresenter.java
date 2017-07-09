package com.example.bill.delta.ui.news.NewsNodes;

import android.util.Log;
import com.example.bill.delta.bean.newsnode.event.NewsNodesEvent;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseMVP.Model;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewsNodesBasePresenter implements NewsNodesBaseMVP.Presenter {

  private static final String TAG = "NewsNodesBasePresenter";

  private NewsNodesBaseMVP.Model mModel;
  private NewsNodesBaseMVP.View mView;

  @Inject
  public NewsNodesBasePresenter(
      Model mModel) {
    this.mModel = mModel;
  }

  public void readNodes() {
    mModel.readNewsNodes();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void showNewsNodes(NewsNodesEvent newsNodesEvent) {
    Log.d(TAG, "showNewsNodes");
    mView.showNodes(newsNodesEvent.getNewsNodeList());
    EventBus.getDefault().removeStickyEvent(newsNodesEvent);
  }

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
