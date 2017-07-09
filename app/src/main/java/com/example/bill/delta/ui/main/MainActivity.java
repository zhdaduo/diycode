package com.example.bill.delta.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.user.Token;
import com.example.bill.delta.bean.user.UserDetailInfo;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.topic.Topics.TopicFragment;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.ui.user.User.UserMVP;
import com.example.bill.delta.ui.user.User.UserModule;
import com.example.bill.delta.ui.user.User.UserPresenter;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingMVP;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingModule;
import com.example.bill.delta.util.Constant;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.view.adapter.main.MainPagerAdapter;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, UserMVP.View, UserFollowingMVP.View {

  private static final String TAG = "MainActivity";

  private ImageView avatar;
  private TextView email;
  private TextView loginName;
  private UserDetailInfo me;

  @BindView(R.id.tab_layout)
  TabLayout tabLayout;
  @BindView(R.id.view_pager)
  ViewPager viewPager;
  @BindView(R.id.fab)
  FloatingActionButton fab;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;
  @BindView(R.id.coordinator)
  CoordinatorLayout coordinator;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.lv)
  ListView listView;

  @Inject
  UserPresenter userPresenter;
  @Inject
  Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, MainActivity.class);
    callingIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    return callingIntent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new UserModule(this), new UserFollowingModule(this))
        .inject(this);

    toolbar.setLogo(R.mipmap.logo_actionbar);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!hasSignedIn()) {
          return;
        }

        if (viewPager.getCurrentItem() == 0) {
          navigator.navigateToCreateTopicActivity(MainActivity.this);
        } else if (viewPager.getCurrentItem() == 1) {
          navigator.navigateToCreateNewsActivity(MainActivity.this);
        }
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    PagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset,
          int positionOffsetPixels) {
        LogUtil.d(TAG, position + " " + positionOffset + " " + positionOffsetPixels);
        if (position == 1) {
          fab.setScaleX(1 - positionOffset);
          fab.setScaleY(1 - positionOffset);
          fab.setAlpha(1 - positionOffset);
        } else if (position == 0 && fab.getAlpha() < 1) {
          fab.setScaleX(1 - positionOffset);
          fab.setScaleY(1 - positionOffset);
          fab.setAlpha(1 - positionOffset);
        }
      }

      @Override
      public void onPageSelected(int position) {
        if (position == 1) {
          fab.setAlpha(1f);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });

    View header = navigationView.getHeaderView(0);
    email = (TextView) header.findViewById(R.id.email);
    loginName = (TextView) header.findViewById(R.id.loginName);
    avatar = (ImageView) header.findViewById(R.id.avatar);
    avatar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!TextUtils.isEmpty(Constant.VALUE_TOKEN)) {
          navigator.navigateToUserActivity(MainActivity.this,
              PrefUtil.getMe(MainActivity.this).getLogin());
        } else {
          startActivityForResult(SignInActivity.getCallingIntent(MainActivity.this),
              SignInActivity.REQUEST_CODE);
        }
      }
    });

    Token token = PrefUtil.getToken(this);
    if (!TextUtils.isEmpty(token.getAccessToken())) {
      me = PrefUtil.getMe(this);
      if (!TextUtils.isEmpty(me.getLogin())
          && !TextUtils.isEmpty(me.getAvatarUrl())
          && !TextUtils.isEmpty(me.getEmail())) {
        showMe(me);
      } else {
        LogUtil.d(TAG, "user is null");
        userPresenter.getMe();
      }
    } else {
      LogUtil.d(TAG, "token is null");
    }
  }

  @Override
  protected void onNewIntent(Intent intent) {
    LogUtil.d(TAG, "onNewIntent");
    super.onNewIntent(intent);
    if (getResources().getString(R.string.logout_intent_action).equals(intent.getAction())) {
      PrefUtil.clearMe(this);
      email.setText("点击图片登录");
      loginName.setText("");
      Glide.with(this)
          .load(R.drawable.shape_glide_img_error)
          .crossFade()
          .bitmapTransform(new CropCircleTransformation(this))
          .error(R.drawable.shape_glide_img_error)
          .into(avatar);
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        navigator.navigateToSettingsActivity(MainActivity.this);
        break;
      case R.id.action_search:
        navigator.navigateToSearchActivity(MainActivity.this);
        break;
      case R.id.action_notification:
        if (hasSignedIn()) {
          navigator.navigateToNotificationActivity(MainActivity.this);
        }
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.nav_post) {
      if (hasSignedIn()) {
        navigator.navigateToMyTopicsActivity(MainActivity.this, "我的帖子", TopicFragment.TYPE_CREATE);
      }
    } else if (id == R.id.nav_collect) {
      if (hasSignedIn()) {
        navigator
            .navigateToMyTopicsActivity(MainActivity.this, "我的收藏", TopicFragment.TYPE_FAVORITE);
      }
    } else if (id == R.id.nav_comment) {
      if (hasSignedIn()) {
        navigator.navigateToMyUserRepliesActivity(MainActivity.this);
      }
    } else if (id == R.id.nav_about) {
      navigator.navigateToAboutActivity(MainActivity.this);
    } else if (id == R.id.nav_setting) {
      navigator.navigateToSettingsActivity(MainActivity.this);
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void getMe(UserDetailInfo userDetailInfo) {
    LogUtil.d(TAG, "getMe: " + userDetailInfo);
    if (userDetailInfo == null) {
      return;
    }
    PrefUtil.saveMe(this, userDetailInfo);
    me = userDetailInfo;
    showMe(userDetailInfo);
  }

  @Override
  public void getUser(UserDetailInfo userDetailInfo) {

  }

  private void showMe(UserDetailInfo userDetailInfo) {
    email.setText(userDetailInfo.getEmail());
    loginName.setText(userDetailInfo.getLogin());
    Glide.with(this)
        .load(userDetailInfo.getAvatarUrl())
        .crossFade()
        .bitmapTransform(new CropCircleTransformation(this))
        .placeholder(R.drawable.shape_glide_img_error)
        .error(R.drawable.shape_glide_img_error)
        .into(avatar);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (userPresenter != null) {
      userPresenter.bind(this);
      userPresenter.start();
    }
    if (!TextUtils.isEmpty(PrefUtil.getMe(this).getEmail())) {
      showMe(PrefUtil.getMe(this));
    }
  }

  @Override
  protected void onStop() {
    if (userPresenter != null) {
      userPresenter.unbind();
      userPresenter.stop();
    }
    super.onStop();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        LogUtil.d(TAG, "resultCode: " + resultCode);
        if (resultCode == SignInActivity.RESULT_OK) {
          userPresenter.getMe();
        }
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  private boolean hasSignedIn() {
    String loginName = PrefUtil.getMe(MainActivity.this).getLogin();
    if (TextUtils.isEmpty(loginName)) {
      Snackbar.make(coordinator, "请先登录", Snackbar.LENGTH_LONG)
          .setAction("登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivityForResult(SignInActivity.getCallingIntent(MainActivity.this),
                  SignInActivity.REQUEST_CODE);
            }
          })
          .show();
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void showFollow(boolean bool) {

  }

  @Override
  public void showUnFollow(boolean bool) {

  }
}
