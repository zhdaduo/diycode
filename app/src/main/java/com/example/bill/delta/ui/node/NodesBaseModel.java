package com.example.bill.delta.ui.node;

import android.util.Log;
import com.example.bill.delta.api.NodeService;
import com.example.bill.delta.bean.topicnode.Node;
import com.example.bill.delta.bean.topicnode.event.NodesEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NodesBaseModel implements NodesBaseMVP.Model {

  private static final String TAG = "NodesBaseModel";

  @Inject
  NodeService service;

  @Inject
  public NodesBaseModel() {
  }

  @Override
  public void readNodes() {
    Call<List<Node>> call = service.readNodes();
    call.enqueue(new Callback<List<Node>>() {
      @Override public void onResponse(Call<List<Node>> call, Response<List<Node>> response) {
        if (response.isSuccessful()) {
          List<Node> nodeList = response.body();
          Log.v(TAG, "nodeList:" + nodeList);
          EventBus.getDefault().postSticky(new NodesEvent(nodeList));
        } else {
          Log.e(TAG, "readNodes STATUS: " + response.code());
          EventBus.getDefault().postSticky(new NodesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Node>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new NodesEvent(null));
      }
    });
  }
}
