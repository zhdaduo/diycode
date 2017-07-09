package com.example.bill.delta.ui.topic.CreateTopicReply;

import android.util.Log;
import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.bean.topic.TopicReply;
import com.example.bill.delta.bean.topic.event.CreateTopicReplyEvent;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTopicReplyModel implements CreateTopicReplyMVP.Model {

  private static final String TAG = "TopicModel";

  @Inject
  TopicService service;

  @Inject
  public CreateTopicReplyModel() {
  }

  @Override
  public void createReply(int id, String body) {
    Call<TopicReply> call = service.createReply(id, body);
    call.enqueue(new Callback<TopicReply>() {
      @Override public void onResponse(Call<TopicReply> call, Response<TopicReply> response) {
        if (response.isSuccessful()) {
          TopicReply topicReply = response.body();
          Log.v(TAG, "topicReply:" + topicReply);
          EventBus.getDefault().postSticky(new CreateTopicReplyEvent(true));
        } else {
          Log.e(TAG, "createReply STATUS: " + response.code());
          EventBus.getDefault().post(new CreateTopicReplyEvent(false));
        }
      }

      @Override public void onFailure(Call<TopicReply> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new CreateTopicReplyEvent(false));
      }
    });
  }
}
