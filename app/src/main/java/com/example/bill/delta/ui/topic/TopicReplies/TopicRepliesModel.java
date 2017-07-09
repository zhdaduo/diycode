package com.example.bill.delta.ui.topic.TopicReplies;

import android.util.Log;
import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.bean.topic.TopicReply;
import com.example.bill.delta.bean.topic.event.TopicRepliesEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;

public class TopicRepliesModel implements TopicRepliesMVP.Model {

  private static final String TAG = "TopicRepliesModel";

  @Inject
  TopicService service;

  @Inject
  public TopicRepliesModel() {
  }

  @Override
  public void getReplies(int id, Integer offset, Integer limit) {
    Call<List<TopicReply>> call = service.getReplies(id, offset, limit);
    call.enqueue(new Callback<List<TopicReply>>() {
      @Override public void onResponse(Call<List<TopicReply>> call,
          retrofit2.Response<List<TopicReply>> response) {
        if (response.isSuccessful()) {
          List<TopicReply> topicReplyList = response.body();
          Log.v(TAG, "topicReplyList:" + topicReplyList);
          EventBus.getDefault().post(new TopicRepliesEvent(topicReplyList));
        } else {
          Log.e(TAG, "getReplies STATUS: " + response.code());
          EventBus.getDefault().post(new TopicRepliesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<TopicReply>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicRepliesEvent(null));
      }
    });
  }
}
