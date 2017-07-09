package com.example.bill.delta.ui.user.UserTopics;

import android.util.Log;
import com.example.bill.delta.api.UserService;
import com.example.bill.delta.bean.topic.Topic;
import com.example.bill.delta.bean.user.event.UserFavoriteTopicsEvent;
import com.example.bill.delta.bean.user.event.UserTopicsEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTopicsModel implements UserTopicsMVP.Model{

  private static final String TAG = "UserTopicsModel";

  @Inject
  UserService service;

  @Inject
  public UserTopicsModel() {
  }

  @Override
  public void getUserCreateTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = service.getUserCreateTopics(loginName, offset, limit);

    call.enqueue(new Callback<List<Topic>>() {
      @Override
      public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new UserTopicsEvent(topicList));
        } else {
          Log.e(TAG, "getUserCreateTopics STATUS: " + response.code());
          EventBus.getDefault().post(new UserTopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserTopicsEvent(null));
      }
    });
  }

  @Override
  public void getUserFavoriteTopics(String loginName, Integer offset, Integer limit) {
    Call<List<Topic>> call = service.getUserFavoriteTopics(loginName, offset, limit);

    call.enqueue(new Callback<List<Topic>>() {
      @Override
      public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
        if (response.isSuccessful()) {
          List<Topic> topicList = response.body();
          Log.v(TAG, "topicList:" + topicList);
          EventBus.getDefault().post(new UserFavoriteTopicsEvent(topicList));
        } else {
          Log.e(TAG, "getUserFavoriteTopics STATUS: " + response.code());
          EventBus.getDefault().post(new UserFavoriteTopicsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Topic>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserFavoriteTopicsEvent(null));
      }
    });
  }
}
