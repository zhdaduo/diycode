package com.example.bill.delta.ui.user.UserReplies;

import android.util.Log;
import com.example.bill.delta.api.UserService;
import com.example.bill.delta.bean.topic.Reply;
import com.example.bill.delta.bean.topic.event.RepliesEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepliesModel implements UserRepliesMVP.Model {

  private static final String TAG = "UserRepliesModel";

  @Inject
  UserService service;

  @Inject
  public UserRepliesModel() {
  }

  @Override
  public void getUserReplies(String loginName, Integer offset, Integer limit) {
    Call<List<Reply>> call = service.getUserReplies(loginName, offset, limit);
    call.enqueue(new Callback<List<Reply>>() {
      @Override
      public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
        if (response.isSuccessful()) {
          List<Reply> replyList = response.body();
          Log.v(TAG, "replyList: " + replyList);
          EventBus.getDefault().postSticky(new RepliesEvent(replyList));
        } else {
          Log.e(TAG, "getUserReplies STATUS: " + response.code());
          EventBus.getDefault().postSticky(new RepliesEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Reply>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new RepliesEvent(null));
      }
    });
  }
}
