package com.example.bill.delta.ui.news.NewsNodes;

import com.example.bill.delta.bean.newsnode.NewsNode;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface NewsNodesBaseMVP {

  interface View extends BaseView {

    void showNodes(List<NewsNode> newsNodeList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void readNewsNodes();
  }
}
