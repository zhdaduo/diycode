package com.example.bill.delta.ui.user.UserReplies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.topic.Reply;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.view.Listener.IReplyListener;
import com.example.bill.delta.view.adapter.reply.ReplyViewProvider;
import com.example.bill.delta.view.widget.DividerListItemDecoration;
import com.example.bill.delta.view.widget.EmptyRecyclerView;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MyUserRepliesActivity extends BaseActivity implements UserRepliesMVP.View {

  private static final String TAG = "MyUserRepliesActivity";
  public static final String LOGIN_NAME = "loginName";

  private MultiTypeAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  private Items items;
  private int offset;
  private String loginName;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rv_replies)
  EmptyRecyclerView rvReplies;
  @BindView(R.id.empty_view)
  TextView emptyView;

  @Inject
  UserRepliesPresenter userRepliesPresenter;
  @Inject Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, MyUserRepliesActivity.class);
    return callingIntent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_my_replies);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new UserRepliesModule(this))
        .inject(this);

    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(Reply.class, new ReplyViewProvider(listener));
    linearLayoutManager = new LinearLayoutManager(this);
    rvReplies.setLayoutManager(linearLayoutManager);
    rvReplies.setAdapter(adapter);
    rvReplies.setEmptyView(emptyView);
    rvReplies.addItemDecoration(new DividerListItemDecoration(getContext()));
    rvReplies.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          //adapter.setStatus(TopicsAdapter.STATUS_LOADING);
          //adapter.notifyDataSetChanged();
          userRepliesPresenter.getReplies(loginName, offset);
        }
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });

    loginName = PrefUtil.getMe(this).getLogin();
    if (TextUtils.isEmpty(loginName)) {
      startActivityForResult(SignInActivity.getCallingIntent(MyUserRepliesActivity.this),
          SignInActivity.REQUEST_CODE);
      this.showToastMessage("请先登录");
    } else {
      getReplies();
    }
  }

  private void getReplies() {
    loginName = PrefUtil.getMe(this).getLogin();
    userRepliesPresenter.getReplies(loginName, offset);
  }

  @Override
  public void showReplies(List<Reply> replyList) {
    if (replyList == null || replyList.isEmpty()) {
      return;
    }
    for (Reply reply : replyList) {
      items.add(reply);
    }
    offset = items.size();
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          getReplies();
        } else {
          this.showToastMessage("放弃登录");
          finish();
        }
        break;
    }
  }

  @Override
  protected Toolbar getToolbar() {
    return toolbar;
  }

  public Context getContext() {
    return this;
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (userRepliesPresenter != null) {
      userRepliesPresenter.bind(this);
      userRepliesPresenter.start();
    }
  }

  @Override
  protected void onStop() {
    if (userRepliesPresenter != null) {
      userRepliesPresenter.unbind();
      userRepliesPresenter.stop();
    }
    super.onStop();
  }

  IReplyListener listener = new IReplyListener() {
    @Override
    public void ReplyListener(View view, int id) {
      navigator.navigateToTopicActivity(view.getContext(), id);
    }
  };
}
