package com.example.bill.delta.ui.node;

import com.example.bill.delta.bean.topicnode.Node;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface NodesBaseMVP {

  interface View extends BaseView {

    void showNodes(List<Node> nodeList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void readNodes();
  }
}
