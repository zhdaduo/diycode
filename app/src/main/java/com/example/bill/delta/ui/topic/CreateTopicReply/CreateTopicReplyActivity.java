package com.example.bill.delta.ui.topic.CreateTopicReply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.ui.base.BaseActivity;
import javax.inject.Inject;

public class CreateTopicReplyActivity extends BaseActivity implements CreateTopicReplyMVP.View {

  public static final String TO = "toSb";
  public static final String TOPIC_ID = "topicId";
  public static final String TOPIC_TITLE = "topicTitle";
  private int id;

  @BindView(R.id.title) TextView title;
  @BindView(R.id.body) EditText body;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Inject CreateTopicReplyPresenter createTopicReplyPresenter;

  public static Intent getCallingIntent(Context context, int id, String title) {
    Intent callingIntent = new Intent(context, CreateTopicReplyActivity.class);
    callingIntent.putExtra(TOPIC_ID, id);
    callingIntent.putExtra(TOPIC_TITLE, title);
    return callingIntent;
  }

  public static Intent getCallingIntentMore(Context context, int id, String title, String to) {
    Intent callingIntent = new Intent(context, CreateTopicReplyActivity.class);
    callingIntent.putExtra(TOPIC_ID, id);
    callingIntent.putExtra(TOPIC_TITLE, title);
    callingIntent.putExtra(TO, to);
    return callingIntent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_create_topic_reply);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new CreateTopicReplyModule(this))
        .inject(this);

    Intent intent = getIntent();
    id = intent.getIntExtra(TOPIC_ID, 0);
    String titleString = intent.getStringExtra(TOPIC_TITLE);
    title.setText(titleString);
    String contentPrefix = intent.getStringExtra(TO);
    if (!TextUtils.isEmpty(contentPrefix)) {
      body.setText(contentPrefix);
      body.setSelection(contentPrefix.length());
    }
    body.requestFocus();
  }

  private void send() {
    if (TextUtils.isEmpty(body.getText())) {
      this.showToastMessage("评论内容不能为空");
      return;
    }
    createTopicReplyPresenter.createTopicReply(id, body.getText().toString());
  }

  @Override
  public void getResult(boolean isSuccessful) {
    if (isSuccessful) {
      finish();
    } else {
      this.showToastMessage("发布失败");
    }
  }

  @Override
  protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      case R.id.action_send:
        send();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_create_topic_reply, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if(createTopicReplyPresenter != null) {
      createTopicReplyPresenter.bind(this);
      createTopicReplyPresenter.start();
    }
  }

  @Override
  protected void onStop() {
    if(createTopicReplyPresenter != null) {
      createTopicReplyPresenter.unbind();
      createTopicReplyPresenter.stop();
    }
    super.onStop();
  }
}
