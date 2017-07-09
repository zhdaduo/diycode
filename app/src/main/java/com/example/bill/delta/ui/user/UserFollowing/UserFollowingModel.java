package com.example.bill.delta.ui.user.UserFollowing;

import android.util.Log;
import com.example.bill.delta.api.UserService;
import com.example.bill.delta.bean.user.UserFollow;
import com.example.bill.delta.bean.user.UserUnFollow;
import com.example.bill.delta.bean.user.event.UserFollowEvent;
import com.example.bill.delta.bean.user.event.UserUnFollowEvent;
import com.example.bill.delta.util.Constant;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFollowingModel implements UserFollowingMVP.Model {

  private static final String TAG = "UserFollowingModel";

  @Inject
  UserService service;

  @Inject
  public UserFollowingModel() {
  }

  @Override
  public void Follow(String loginName) {
    Call<UserFollow> call = service.followUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
    call.enqueue(new Callback<UserFollow>() {
      @Override
      public void onResponse(Call<UserFollow> call, Response<UserFollow> response) {
        if (response.isSuccessful()) {
          UserFollow userFollow = response.body();
          Log.v(TAG, "userFollow: " + userFollow);
          EventBus.getDefault().post(new UserFollowEvent(userFollow.getOk() == 1));
        } else {
          Log.v(TAG, "userFollow: STATUS " + response.code());
          EventBus.getDefault().post(new UserFollowEvent(false));
        }
      }

      @Override
      public void onFailure(Call<UserFollow> call, Throwable t) {
        EventBus.getDefault().post(new UserFollowEvent(false));
      }
    });
  }

  @Override
  public void unFollow(String loginName) {
    Call<UserUnFollow> call = service.unFollowUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
    call.enqueue(new Callback<UserUnFollow>() {
      @Override
      public void onResponse(Call<UserUnFollow> call, Response<UserUnFollow> response) {
        if (response.isSuccessful()) {
          UserUnFollow userUnFollow = response.body();
          Log.v(TAG, "userUnFollow: " + userUnFollow);
          EventBus.getDefault().post(new UserUnFollowEvent(userUnFollow.getOk() == 1));
        } else {
          Log.v(TAG, "userUnFollow: STATUS " + response.code());
          EventBus.getDefault().post(new UserUnFollowEvent(false));
        }
      }

      @Override
      public void onFailure(Call<UserUnFollow> call, Throwable t) {
        EventBus.getDefault().post(new UserUnFollowEvent(false));
      }
    });
  }
}
