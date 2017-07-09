package com.example.bill.delta.ui.user.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.user.UserDetailInfo;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.topic.Topics.TopicFragment;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingMVP;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingModule;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingPresenter;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.util.PrefUtil;
import com.jaeger.library.StatusBarUtil;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserActivity extends BaseActivity implements UserMVP.View, UserFollowingMVP.View {

  private static final String TAG = "UserActivity";
  public static final String LOGIN_NAME = "loginName";
  public static final String Followed = "isFollowed";
  private String loginName;
  private boolean isfollowed;
  private FragmentManager fragmentManager;
  private TopicFragment fragmentCreate;
  private TopicFragment fragmentFavorite;
  private TopicFragment fragmentUserFollowing;
  private String[] fragNames;

  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.name) TextView name;
  @BindView(R.id.topic_num) TextView topicNum;
  @BindView(R.id.favorite_num) TextView favoriteNum;
  @BindView(R.id.followed_num) TextView followNum;
  @BindView(R.id.follow) Button follow;
  @BindView(R.id.coordinator) CoordinatorLayout coordinator;

  @Inject UserPresenter userPresenter;
  @Inject UserFollowingPresenter userFollowingPresenter;

  public static Intent getCallingIntent(Context context, String loginName) {
    Intent callingIntent = new Intent(context, UserActivity.class);
    callingIntent.putExtra(LOGIN_NAME, loginName);
    return callingIntent;
  }

  public static Intent getCallingIntent(Context context, String loginName, boolean isFollowed) {
    Intent callingIntent = new Intent(context, UserActivity.class);
    callingIntent.putExtra(LOGIN_NAME, loginName);
    callingIntent.putExtra(Followed, isFollowed);
    return callingIntent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_user);
    StatusBarUtil.setColorForSwipeBack(this, 0x515a74);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new UserModule(this), new UserFollowingModule(this))
        .inject(this);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    loginName = intent.getStringExtra(LOGIN_NAME);
    isfollowed = intent.getBooleanExtra(Followed, false);
    userPresenter.getUser(loginName);

    //if login, show the user info
    showLoginedUserInfo();

    updatefollow(false);

    fragmentManager = getSupportFragmentManager();
    fragNames = new String[]{"TYPE_FAVORITE", "TYPE_USERFOLLOWING", "TYPE_CREATE"};

    //initialize screen : show user's create topic
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.container, TopicFragment.newInstance(loginName, TopicFragment.TYPE_CREATE), fragNames[0]);
    //fragmentTransaction.addToBackStack(fragNames[0]);
    fragmentTransaction.commit();
  }

  @OnClick(R.id.back) void onBack() {
    this.finish();
  }

  @OnClick({R.id.favorite, R.id.favorite_num}) void onFavorite() {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    hideAllFrags(fragmentTransaction);
        if (fragmentFavorite == null) {
          fragmentFavorite = TopicFragment.newInstance(loginName, TopicFragment.TYPE_FAVORITE);
          fragmentTransaction.add(R.id.container, fragmentFavorite, fragNames[0]);
          //fragmentTransaction.addToBackStack(fragNames[0]);
        } else {
          fragmentTransaction.show(fragmentFavorite);
        }
    fragmentTransaction.commit();
  }

  @OnClick({R.id.followed, R.id.followed_num}) void onFollowed() {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    hideAllFrags(fragmentTransaction);
    if (fragmentUserFollowing == null) {
      fragmentUserFollowing = TopicFragment.newInstance(loginName, TopicFragment.TYPE_USERFOLLOWING);
      fragmentTransaction.add(R.id.container, fragmentUserFollowing, fragNames[1]);
      //fragmentTransaction.addToBackStack(fragNames[1]);
    } else {
      fragmentTransaction.show(fragmentUserFollowing);
    }
    fragmentTransaction.commit();
  }

  private void hideAllFrags(FragmentTransaction fragmentTransaction) {
    Log.i("hideAllFrags", "hideAllFrags");
    for (String name : fragNames) {
      Fragment fragment = fragmentManager.findFragmentByTag(name);
      if (fragment != null && !fragment.isHidden()) {
        fragmentTransaction.hide(fragment);
      }
    }
  }

  @OnClick({R.id.topic, R.id.topic_num}) void onCreatedTopic() {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    hideAllFrags(fragmentTransaction);
    if (fragmentCreate == null) {
      fragmentCreate =   TopicFragment.newInstance(loginName, TopicFragment.TYPE_CREATE);
      fragmentTransaction.add(R.id.container, fragmentCreate, fragNames[2]);
      //fragmentTransaction.addToBackStack(fragNames[2]);
    } else {
      fragmentTransaction.show(fragmentCreate);
    }
    fragmentTransaction.commit();
  }

  @OnClick(R.id.follow) void onUpdateFollow() {
    updatefollow(true);
  }

  private void updatefollow (boolean reverse) {
    String loginName = PrefUtil.getMe(getApplicationContext()).getLogin();
    if (TextUtils.isEmpty(loginName) && reverse) {
      //EventBus.getDefault().post(new SignInEvent());
      signIn();
      return;
    }
    if(reverse) {
      isfollowed = !isfollowed;
    }
    if(isfollowed) {
      follow.setBackground(getResources().getDrawable(R.drawable.shape_user_followed_bg));
      follow.setTextColor(getResources().getColor(R.color.color_secondary_999999));
      follow.setText("已关注");
    } else {
      follow.setBackground(getResources().getDrawable(R.drawable.shape_user_follow_bg));
      follow.setTextColor(getResources().getColor(R.color.white));
      follow.setText("+关注");
    }
  }

  public void showLoginedUserInfo (){
    if (PrefUtil.getMe(this).getLogin().equals(loginName)) {
      follow.setVisibility(View.GONE);
      name.setText(PrefUtil.getMe(this).getLogin());
      Glide.with(this)
          .load(PrefUtil.getMe(this).getAvatarUrl())
          .crossFade()
          .bitmapTransform(new CropCircleTransformation(this))
          .error(R.drawable.shape_glide_img_error)
          .into(avatar);
    }
  }

  @Override
  public void getMe(UserDetailInfo userDetailInfo) {

  }

  @Override
  public void getUser(UserDetailInfo userDetailInfo) {
    LogUtil.d(TAG, "getUser: " + userDetailInfo);
    // qualification : userDetailInfo.getLogin().equals(loginName)
    // fixed bug: go back to previous user screen, keep it's original preview.
    if (userDetailInfo != null && userDetailInfo.getLogin().equals(loginName)) {
      name.setText(userDetailInfo.getLogin());
      topicNum.setText(String.valueOf(userDetailInfo.getTopicsCount()));
      followNum.setText(String.valueOf(userDetailInfo.getFollowingCount()));
      favoriteNum.setText(String.valueOf(userDetailInfo.getFavoritesCount()));
      Glide.with(this)
          .load(userDetailInfo.getAvatarUrl())
          .bitmapTransform(new CropCircleTransformation(this))
          .crossFade()
          .placeholder(R.drawable.shape_glide_img_error)
          .error(R.drawable.shape_glide_img_error)
          .into(avatar);
    }
  }

  @Override
  protected Toolbar getToolbar() {
    return null;
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (userPresenter != null || userFollowingPresenter != null) {
      userPresenter.bind(this);
      userFollowingPresenter.bind(this);
      userPresenter.start();
      userFollowingPresenter.start();
    }
  }

  @Override
  protected void onStop() {
    if (userPresenter != null || userFollowingPresenter != null) {
      userPresenter.unbind();
      userFollowingPresenter.unbind();
      userPresenter.stop();
      userFollowingPresenter.stop();
    }
    super.onStop();
  }

  @Override
  protected void onPause() {
    if (isfollowed) {
      userFollowingPresenter.followTopic(loginName);
    } else {
      userFollowingPresenter.unFollowTopic(loginName);
    }
    super.onPause();
  }

  @Override
  public void showFollow(boolean bool) {

  }

  @Override
  public void showUnFollow(boolean bool) {

  }

  private void signIn() {
    Snackbar.make(coordinator, "请先登录", Snackbar.LENGTH_LONG)
        .setAction("登录", new View.OnClickListener() {
          @Override public void onClick(View v) {
            startActivityForResult(SignInActivity.getCallingIntent(UserActivity.this),
                SignInActivity.REQUEST_CODE);
          }
        })
        .show();
  }
}
