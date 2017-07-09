package com.example.bill.delta.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.R;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.topic.Topics.TopicFragment;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.util.PrefUtil;
/**
 * my topics screen.
 */

public class MyTopicsActivity extends BaseActivity {

  public static final String TITLE = "title";
  public static final String TYPE = "type";
  @BindView(R.id.toolbar) Toolbar toolbar;
  private String title;
  private int type;

  public static Intent getCallingIntent(Context context, String title, int type) {
    Intent callingIntent = new Intent(context, MyTopicsActivity.class);
    callingIntent.putExtra(TITLE, title);
    callingIntent.putExtra(TYPE, type);
    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_my_topics);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    if (intent != null) {
      title = intent.getStringExtra(TITLE);
      type = intent.getIntExtra(TYPE, TopicFragment.TYPE_ALL);
    }

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(title);
    }

    String loginName = PrefUtil.getMe(this).getLogin();
    if (TextUtils.isEmpty(loginName)) {
      startActivityForResult(SignInActivity.getCallingIntent(MyTopicsActivity.this),
          SignInActivity.REQUEST_CODE);
      this.showToastMessage("请先登录");
      return;
    }

    addTopicFragment();
  }

  @Override protected Toolbar getToolbar() {
    return toolbar;
  }

  private void addTopicFragment() {
    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
    TopicFragment topicFragment =
        fragment == null ? TopicFragment.newInstance(PrefUtil.getMe(this).getLogin(), type)
            : (TopicFragment) fragment;
    getSupportFragmentManager().beginTransaction().add(R.id.container, topicFragment).commit();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          addTopicFragment();
        } else {
          this.showToastMessage("放弃登录");
          finish();
        }
        break;
    }
  }
}
