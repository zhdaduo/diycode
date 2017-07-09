package com.example.bill.delta.ui.news.CreateNews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.bean.newsnode.NewsNode;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseMVP;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseModule;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBasePresenter;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.util.UrlUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

public class CreateNewsActivity extends BaseActivity implements CreateNewsBaseMVP.View,
    NewsNodesBaseMVP.View {

  private static final String TAG = "CreateNewsActivity";
  private List<NewsNode> mNewsNodeList;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.section_name) Spinner sectionName;
  @BindView(R.id.title) EditText title;
  @BindView(R.id.link) EditText link;
  @BindView(R.id.coordinator) CoordinatorLayout coordinator;

  @Inject
  CreateNewsBasePresenter createNewsBasePresenter;
  @Inject
  NewsNodesBasePresenter newsNodesBasePresenter;
  @Inject Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, CreateNewsActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_create_news);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new CreateNewsBaseModule(this), new NewsNodesBaseModule(this))
        .inject(this);

    Intent intent = getIntent();
    if (Intent.ACTION_SEND.equals(intent.getAction())) {
      String linkText = intent.getStringExtra(Intent.EXTRA_TEXT);
      String titleText = intent.getStringExtra(Intent.EXTRA_SUBJECT);
      LogUtil.d(TAG, "getIntent: " + linkText + " " + titleText);

      if (!TextUtils.isEmpty(linkText) && TextUtils.isEmpty(titleText)) {
        titleText = linkText;
        linkText = UrlUtil.getUrl(linkText);
        if (TextUtils.isEmpty(linkText)) {
          linkText = titleText;
        } else {
          titleText = titleText.replace(linkText, "");
        }
      }

      link.setText(linkText);
      title.setText(titleText);
    }

    newsNodesBasePresenter.readNodes();
    String loginName = PrefUtil.getMe(this).getLogin();
    if (TextUtils.isEmpty(loginName)) {
      Snackbar.make(coordinator, "请先登录", Snackbar.LENGTH_INDEFINITE)
          .setAction("登录", new View.OnClickListener() {
            @Override public void onClick(View v) {
              startActivityForResult(
                  SignInActivity.getCallingIntent(CreateNewsActivity.this),
                  SignInActivity.REQUEST_CODE);
            }
          })
          .show();
    }
  }

  private void createNews() {
    if (isTextEmpty()) {
      return;
    }
    String section = sectionName.getDisplay().getName();
    int id = 11;
    for (NewsNode node : mNewsNodeList) {
      if (node.getName().equals(section)) {
        id = node.getId();
      }
    }
    createNewsBasePresenter.createNews(title.getText().toString(),
        link.getText().toString(), id);
  }

  private boolean isTextEmpty() {
    boolean result = false;
    if (TextUtils.isEmpty(title.getText().toString())) {
      this.showToastMessage("标题不能为空");
      result = true;
    } else if (TextUtils.isEmpty(link.getText().toString())) {
      this.showToastMessage("链接不能为空");
      result = true;
    } else if (TextUtils.isEmpty(sectionName.getDisplay().getName())) {
      this.showToastMessage("分类不能为空");
      result = true;
    }
    return result;
  }

  @Override
  public void showNews(News news) {
    if (news != null) {
      this.showToastMessage("分享成功");
      navigator.navigateToWebActivity(CreateNewsActivity.this, news.getAddress());
      finish();
    } else {
      this.showToastMessage("分享失败");
    }
  }

  @Override
  protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override
  public void showNodes(List<NewsNode> newsNodeList) {
    if (newsNodeList == null || newsNodeList.isEmpty()) {
      return;
    }
    List<String> temp = getSectionNames(newsNodeList);
    String[] mSectionNames = temp.toArray(new String[temp.size()]);
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<String> adapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mSectionNames);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    sectionName.setAdapter(adapter);
  }

  private List<String> getSectionNames(List<NewsNode> newsNodeList) {
    this.mNewsNodeList = newsNodeList;
    List<String> parents = new ArrayList<>();
    Set<String> set = new HashSet<>();
    for (NewsNode newsNode : newsNodeList) {
      String element = newsNode.getName();
      if (set.add(element)) parents.add(element);
    }
    return parents;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          if (this.mNewsNodeList.size() == 0) {
            newsNodesBasePresenter.readNodes();
          }
        } else {
          this.showToastMessage("放弃登录");
          finish();
        }
        break;
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
      case R.id.action_send:
        createNews();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_create_news, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (createNewsBasePresenter != null || newsNodesBasePresenter != null) {
      newsNodesBasePresenter.bind(this);
      createNewsBasePresenter.bind(this);
      newsNodesBasePresenter.start();
      createNewsBasePresenter.start();
    }
  }

  @Override
  protected void onStop() {
    if (createNewsBasePresenter != null || newsNodesBasePresenter != null) {
      createNewsBasePresenter.unbind();
      newsNodesBasePresenter.unbind();
      createNewsBasePresenter.stop();
      createNewsBasePresenter.stop();
    }
    super.onStop();
  }
}
