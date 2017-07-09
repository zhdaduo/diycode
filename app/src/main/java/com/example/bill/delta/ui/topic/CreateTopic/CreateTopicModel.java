package com.example.bill.delta.ui.topic.CreateTopic;

import android.util.Log;
import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.bean.topic.event.CreateTopicEvent;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTopicModel implements CreateTopicMVP.Model {

  private static final String TAG = "CreateTopicModel";

  @Inject
  TopicService service;

  @Inject
  public CreateTopicModel() {
  }

  @Override
  public void newTopic(String title, String body, int nodeId) {
    Call<TopicDetail> call = service.newTopic(title, body, nodeId);
    call.enqueue(new Callback<TopicDetail>() {
      @Override
      public void onResponse(Call<TopicDetail> call, Response<TopicDetail> response) {
        if (response.isSuccessful()) {
          TopicDetail topicDetail = response.body();
          Log.v(TAG, "topicDetail: " + topicDetail);
          EventBus.getDefault().postSticky(new CreateTopicEvent(topicDetail));
          EventBus.getDefault().post(new CreateTopicEvent(topicDetail));
        } else {
          Log.e(TAG, "newTopic STATUS: " + response.code());
          EventBus.getDefault().post(new CreateTopicEvent(null));
        }
      }

      @Override public void onFailure(Call<TopicDetail> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new CreateTopicEvent(null));
      }
    });
  }
}
