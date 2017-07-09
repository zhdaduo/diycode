package com.example.bill.delta.ui.news.NewsBase;

import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface NewsBaseMVP {

  interface View extends BaseView {

    void showNews(List<News> newsList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void readNews(Integer nodeId, Integer offset, Integer limit);
  }
}
