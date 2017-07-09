package com.example.bill.delta.ui.site;

import com.example.bill.delta.bean.site.Site;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface SiteBaseMVP {

  interface View extends BaseView {

    void showSite(List<Site> siteList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void getSite();
  }
}
