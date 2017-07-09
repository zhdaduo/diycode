package com.example.bill.delta.ui.site;

import com.example.bill.delta.bean.site.event.SiteEvent;
import com.example.bill.delta.ui.site.SiteBaseMVP.Model;
import com.example.bill.delta.ui.site.SiteBaseMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SiteBasePresenter implements SiteBaseMVP.Presenter {

  private static final String TAG = "SiteBasePresenter";

  private SiteBaseMVP.Model mModel;
  private SiteBaseMVP.View mView;

  @Inject
  public SiteBasePresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showSite(SiteEvent siteEvent) {
    mView.showSite(siteEvent.getSiteList());
  }

  public void getSite() {
    mModel.getSite();
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
