package com.example.bill.delta.ui.user.UserFollow;

import android.util.Log;
import com.example.bill.delta.api.UserService;
import com.example.bill.delta.bean.user.UserInfo;
import com.example.bill.delta.bean.user.event.UserFollowingEvent;
import com.example.bill.delta.util.Constant;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;

public class UserFollowModel implements UserFollowMVP.Model {

  private static final String TAG = "UserFollowModel";
  private UserInfo userInfo;

  @Inject
  UserService service;

  @Inject
  public UserFollowModel() {

  }

  @Override
  public void getUserFollowing(String loginName, Integer offset) {
    Call<List<UserInfo>> call = service.getUserFollowing(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName, offset, null);
    call.enqueue(new Callback<List<UserInfo>>() {
      @Override public void onResponse(Call<List<UserInfo>> call,
          retrofit2.Response<List<UserInfo>> response) {
        if (response.isSuccessful()) {
          List<UserInfo> userInfoList = response.body();
          Log.v(TAG, "UserFollowingList:" + userInfoList);
          EventBus.getDefault().post(new UserFollowingEvent(userInfoList));
        } else {
          Log.e(TAG, "getUserFollowing STATUS: " + response.code());
          EventBus.getDefault().post(new UserFollowingEvent(null));
        }
      }

      @Override public void onFailure(Call<List<UserInfo>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UserFollowingEvent(null));
      }
    });
  }

}
