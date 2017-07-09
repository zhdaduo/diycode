package com.example.bill.delta.ui.user.User;

import android.util.Log;
import com.example.bill.delta.api.UserService;
import com.example.bill.delta.bean.user.UserDetailInfo;
import com.example.bill.delta.bean.user.event.MeEvent;
import com.example.bill.delta.bean.user.event.UserDetailInfoEvent;
import com.example.bill.delta.util.Constant;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;

public class UserModel implements UserMVP.Model {

  private static final String TAG = "UserRepliesModel";

  @Inject
  UserService service;

  @Inject
  public UserModel() {
  }

  @Override
  public void getMe() {
    Call<UserDetailInfo> call =
        service.getMe(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN);
    call.enqueue(new Callback<UserDetailInfo>() {
      @Override public void onResponse(Call<UserDetailInfo> call,
          retrofit2.Response<UserDetailInfo> response) {
        if (response.isSuccessful()) {
          UserDetailInfo userDetailInfo = response.body();
          Log.d(TAG, "userDetailInfo: " + userDetailInfo);
          EventBus.getDefault().post(new MeEvent(userDetailInfo));
        } else {
          Log.e(TAG, "getMe STATUS: " + response.code());
          EventBus.getDefault().post(new MeEvent(null));
        }
      }

      @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new MeEvent(null));
      }
    });
  }

  @Override
  public void getUser(String loginName) {
    Call<UserDetailInfo> call =
        service.getUser(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, loginName);
    call.enqueue(new Callback<UserDetailInfo>() {
      @Override public void onResponse(Call<UserDetailInfo> call,
          retrofit2.Response<UserDetailInfo> response) {
        if (response.isSuccessful()) {
          UserDetailInfo userDetailInfo = response.body();
          Log.d(TAG, "user: " + userDetailInfo);
          EventBus.getDefault().postSticky(new UserDetailInfoEvent(userDetailInfo));
        } else {
          Log.e(TAG, "getUser STATUS: " + response.code());
          EventBus.getDefault().postSticky(new UserDetailInfoEvent(null));
        }
      }

      @Override public void onFailure(Call<UserDetailInfo> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new UserDetailInfoEvent(null));
      }
    });
  }
}
