package com.example.bill.delta.ui.news.NewsBase;

import android.util.Log;
import com.example.bill.delta.api.NewsService;
import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.bean.news.event.NewsEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsBaseModel implements NewsBaseMVP.Model {

  private static final String TAG = "NewsBaseModel";

  @Inject
  NewsService service;

  @Inject
  public NewsBaseModel() {
  }

  @Override
  public void readNews(Integer nodeId, Integer offset, Integer limit) {
    Call<List<News>> call = service.readNews(nodeId, offset, limit);
    call.enqueue(new Callback<List<News>>() {
      @Override public void onResponse(Call<List<News>> call, Response<List<News>> response) {
        if (response.isSuccessful()) {
          List<News> newsList = response.body();
          Log.v(TAG, "newsList:" + newsList);
          EventBus.getDefault().post(new NewsEvent(newsList));
        } else {
          Log.e(TAG, "readNews STATUS: " + response.code());
          EventBus.getDefault().post(new NewsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<News>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new NewsEvent(null));
      }
    });
  }
}
