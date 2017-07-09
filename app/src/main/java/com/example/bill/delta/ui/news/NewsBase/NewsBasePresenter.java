package com.example.bill.delta.ui.news.NewsBase;

import com.example.bill.delta.bean.news.event.NewsEvent;
import com.example.bill.delta.ui.news.NewsBase.NewsBaseMVP.Model;
import com.example.bill.delta.ui.news.NewsBase.NewsBaseMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewsBasePresenter implements NewsBaseMVP.Presenter {

  private static final String TAG = "NewsBasePresenter";

  private NewsBaseMVP.Model mModel;
  private NewsBaseMVP.View mView;

  @Inject
  public NewsBasePresenter(Model mModel) {
    this.mModel = mModel;
  }

  public void readNews(int offset) {
    mModel.readNews(null, offset, null);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNews(NewsEvent event) {
    mView.showNews(event.getNewsList());
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
