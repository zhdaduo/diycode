package com.example.bill.delta.ui.news.NewsNodes;

import android.util.Log;
import com.example.bill.delta.api.NewsNodeService;
import com.example.bill.delta.bean.newsnode.NewsNode;
import com.example.bill.delta.bean.newsnode.event.NewsNodesEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsNodesBaseModel implements NewsNodesBaseMVP.Model {

  private static final String TAG = "NewsNodesBaseModel";

  @Inject
  NewsNodeService service;

  @Inject
  public NewsNodesBaseModel() {
  }

  @Override
  public void readNewsNodes() {
    Call<List<NewsNode>> call = service.readNewsNodes();
    call.enqueue(new Callback<List<NewsNode>>() {
      @Override
      public void onResponse(Call<List<NewsNode>> call, Response<List<NewsNode>> response) {
        if (response.isSuccessful()) {
          List<NewsNode> newsNodeList = response.body();
          Log.v(TAG, "newsNodeList:" + newsNodeList);
          EventBus.getDefault().postSticky(new NewsNodesEvent(newsNodeList));
        } else {
          Log.e(TAG, "readNewsNodes STATUS: " + response.code());
          EventBus.getDefault().postSticky(new NewsNodesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<NewsNode>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new NewsNodesEvent(null));
      }
    });
  }
}
