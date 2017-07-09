package com.example.bill.delta.ui.node;

import android.util.Log;
import com.example.bill.delta.bean.topicnode.event.NodesEvent;
import com.example.bill.delta.ui.node.NodesBaseMVP.Model;
import com.example.bill.delta.ui.node.NodesBaseMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NodesBasePresenter implements NodesBaseMVP.Presenter {

  private static final String TAG = "NodesBasePresenter";

  private NodesBaseMVP.Model mModel;
  private NodesBaseMVP.View mView;

  @Inject
  public NodesBasePresenter(Model mModel) {
    this.mModel = mModel;
  }

  public void readNodes() {
    mModel.readNodes();
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void showNodes(NodesEvent nodesEvent) {
    Log.d(TAG, "showNodes");
    mView.showNodes(nodesEvent.getNodeList());
    EventBus.getDefault().removeStickyEvent(nodesEvent);
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
