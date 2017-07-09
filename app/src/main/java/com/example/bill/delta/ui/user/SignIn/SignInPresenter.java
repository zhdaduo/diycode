package com.example.bill.delta.ui.user.SignIn;

import com.example.bill.delta.bean.user.event.TokenEvent;
import com.example.bill.delta.ui.user.SignIn.SignInMVP.Model;
import com.example.bill.delta.ui.user.SignIn.SignInMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SignInPresenter implements SignInMVP.Presenter {

  private static final String TAG = "SignInPresenter";

  private SignInMVP.Model mModel;
  private SignInMVP.View mView;

  @Inject
  public SignInPresenter(Model mModel) {
    this.mModel = mModel;
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void getToken(TokenEvent tokenEvent) {
    mView.getToken(tokenEvent.getToken());
  }

  public void getToken(String username, String password) {
    mModel.getToken(username, password);
  }


  public void bind(View view) {
    this.mView = view;
  }

  @Override
  public void unbind() {
    this.mView = null;
  }

  @Override
  public void start() {
    EventBus.getDefault().register(this);
  }

  @Override
  public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
