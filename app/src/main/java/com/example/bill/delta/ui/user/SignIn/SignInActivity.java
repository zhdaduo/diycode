package com.example.bill.delta.ui.user.SignIn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.user.Token;
import com.example.bill.delta.bean.user.UserDetailInfo;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.user.User.UserMVP;
import com.example.bill.delta.ui.user.User.UserModule;
import com.example.bill.delta.ui.user.User.UserPresenter;
import com.example.bill.delta.util.PrefUtil;
import javax.inject.Inject;

public class SignInActivity extends BaseActivity implements SignInMVP.View, UserMVP.View {

  public static final int REQUEST_CODE = 1;
  public static final int RESULT_OK = 200;
  public static final int RESULT_ERROR = 401;

  @BindView(R.id.name) EditText name;
  @BindView(R.id.password) EditText password;
  @BindView(R.id.sign_in) Button signIn;
  @BindView(R.id.sign_github) ImageView signGithub;
  @BindView(R.id.sign_weibo) ImageView signWeibo;
  @BindView(R.id.sign_up) TextView signUp;
  @BindView(R.id.forget_password) TextView forgetPassword;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Inject
  SignInPresenter signInPresenter;
  @Inject
  UserPresenter userPresenter;

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, SignInActivity.class);
    return callingIntent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_sign_in);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new SignInModule(this), new UserModule(this))
        .inject(this);

    signIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String username = name.getText().toString();
        String passwordString = password.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwordString)) {
          showToastMessage("Email / 用户名或密码为空");
          return;
        }
        signInPresenter.getToken(username, passwordString);
      }
    });
  }

  @Override
  public void getToken(Token token) {
    if (token != null) {
      PrefUtil.saveToken(this, token);
      userPresenter.getMe();
    } else {
      this.showToastMessage("Email / 用户名或密码错误，登录失败");
    }
  }

  @Override
  protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override
  public void getMe(UserDetailInfo userDetailInfo) {
    if (userDetailInfo == null) {
      this.showToastMessage("网络出问题了，登录失败");
      setResult(RESULT_ERROR);
    } else {
      PrefUtil.saveMe(this, userDetailInfo);
      this.showToastMessage("登录成功");
      setResult(RESULT_OK);
    }
    finish();
  }

  @Override
  public void getUser(UserDetailInfo userDetailInfo) {

  }

  @Override
  protected void onStart() {
    super.onStart();
    if (signInPresenter != null || userPresenter != null) {
      signInPresenter.bind(this);
      userPresenter.bind(this);
      signInPresenter.start();
      userPresenter.start();
    }
  }

  @Override
  protected void onStop() {
    if (signInPresenter != null || userPresenter != null) {
      signInPresenter.unbind();
      userPresenter.unbind();
      signInPresenter.stop();
      userPresenter.stop();
    }
    super.onStop();
  }
}
