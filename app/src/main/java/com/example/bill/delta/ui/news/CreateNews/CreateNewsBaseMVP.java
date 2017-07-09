package com.example.bill.delta.ui.news.CreateNews;

import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;

public interface CreateNewsBaseMVP {

  interface View extends BaseView {

    void showNews(News news);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void createNews(String title, String address, Integer node_id);
  }
}
