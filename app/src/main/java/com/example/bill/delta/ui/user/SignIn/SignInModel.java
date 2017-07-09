package com.example.bill.delta.ui.user.SignIn;

import android.util.Log;
import com.example.bill.delta.api.UserService;
import com.example.bill.delta.bean.user.Token;
import com.example.bill.delta.bean.user.event.TokenEvent;
import com.example.bill.delta.util.Constant;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;

public class SignInModel implements SignInMVP.Model {

  private static final String TAG = "SignInModel";

  @Inject
  UserService service;

  @Inject
  public SignInModel() {
  }

  @Override
  public void getToken(String username, String password) {
    Call<Token> call =
        service.getToken(Constant.VALUE_CLIENT_ID, Constant.VALUE_CLIENT_SECRET,
            Constant.VALUE_GRANT_TYPE_PASSWORD, username, password);
    call.enqueue(new Callback<Token>() {
      @Override public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
        if (response.isSuccessful()) {
          Token token = response.body();
          //Log.d(TAG, "token: " + token);
          EventBus.getDefault().post(new TokenEvent(token));
        } else {
          EventBus.getDefault().post(new TokenEvent(null));
          Log.e(TAG, "getToken STATUS: " + response.code());
        }
      }

      @Override public void onFailure(Call<Token> call, Throwable t) {
        Log.d(TAG, t.getMessage());
        EventBus.getDefault().post(new TokenEvent(null));
      }
    });
  }
}
