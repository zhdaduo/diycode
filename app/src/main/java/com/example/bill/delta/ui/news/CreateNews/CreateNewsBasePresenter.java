package com.example.bill.delta.ui.news.CreateNews;

import com.example.bill.delta.bean.news.event.CreateNewsEvent;
import com.example.bill.delta.ui.news.CreateNews.CreateNewsBaseMVP.Model;
import com.example.bill.delta.ui.news.CreateNews.CreateNewsBaseMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CreateNewsBasePresenter implements CreateNewsBaseMVP.Presenter {

  private static final String TAG = "CreateNewsBasePresenter";

  private CreateNewsBaseMVP.Model mModel;
  private CreateNewsBaseMVP.View mView;

  @Inject
  public CreateNewsBasePresenter(
      Model mModel) {
    this.mModel = mModel;
  }

  public void createNews(String title, String address, Integer node_id) {
    mModel.createNews(title, address, node_id);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNews(CreateNewsEvent event) {
    mView.showNews(event.getNews());
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
