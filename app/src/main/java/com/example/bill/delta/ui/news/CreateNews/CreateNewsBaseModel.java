package com.example.bill.delta.ui.news.CreateNews;

import android.util.Log;
import com.example.bill.delta.api.NewsService;
import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.bean.news.event.CreateNewsEvent;
import com.example.bill.delta.util.Constant;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewsBaseModel implements CreateNewsBaseMVP.Model {

  private static final String TAG = "CreateNewsBaseModel";

  @Inject
  NewsService service;

  @Inject
  public CreateNewsBaseModel() {
  }

  @Override
  public void createNews(String title, String address, Integer node_id) {
    Call<News> call =
        service.createNews(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, title, address,
            node_id);
    call.enqueue(new Callback<News>() {
      @Override public void onResponse(Call<News> call, Response<News> response) {
        if (response.isSuccessful()) {
          News news = response.body();
          Log.v(TAG, "news: " + news);
          EventBus.getDefault().post(new CreateNewsEvent(news));
        } else {
          Log.e(TAG, "createNews STATUS: " + response.code());
          EventBus.getDefault().post(new CreateNewsEvent(null));
        }
      }

      @Override public void onFailure(Call<News> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new CreateNewsEvent(null));
      }
    });
  }
}
