package com.example.bill.delta.ui.topic.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.bean.topic.TopicReply;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesMVP;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesModule;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesPresenter;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.view.Listener.ITopicListener;
import com.example.bill.delta.view.adapter.topic.Footer;
import com.example.bill.delta.view.adapter.topic.FooterViewProvider;
import com.example.bill.delta.view.adapter.topic.TopicDetailViewProvider;
import com.example.bill.delta.view.adapter.topic.TopicReplyViewProvider;
import com.example.bill.delta.view.adapter.topic.TopicReplyWithTopic;
import com.example.bill.delta.view.widget.DividerListItemDecoration;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TopicActivity extends BaseActivity implements TopicMVP.View, TopicRepliesMVP.View {

  public static final String ID = "topicId";
  private static final String TAG = "TopicActivity";

  private TopicDetail topicDetail;
  private MultiTypeAdapter adapter;
  private Items items = new Items();
  private LinearLayoutManager linearLayoutManager;
  private int topicId;
  private int offset;
  private boolean noMoreReplies;

  @BindView(R.id.rv) RecyclerView rv;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.coordinator) CoordinatorLayout coordinator;

  @Inject
  TopicPresenter topicPresenter;

  @Inject
  TopicRepliesPresenter topicRepliesPresenter;

  @Inject Navigator navigator;

  public static Intent getCallingIntent(Context context, int id) {
    Intent callingIntent = new Intent(context, TopicActivity.class);
    callingIntent.putExtra(ID, id);
    return callingIntent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    LogUtil.d(TAG, "onCreate");
    setContentView(R.layout.activity_topic);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new TopicModule(this), new TopicRepliesModule(this))
        .inject(this);

    Intent intent = getIntent();
    topicId = intent.getIntExtra(ID, 0);
    LogUtil.d(TAG, "topicId: " + topicId);
    linearLayoutManager = new LinearLayoutManager(this);
    rv.setLayoutManager(linearLayoutManager);

    if (items != null) {  adapter = new MultiTypeAdapter(items);}
    adapter.register(TopicDetail.class, new TopicDetailViewProvider(listener));
    adapter.register(TopicReplyWithTopic.class, new TopicReplyViewProvider(listener));
    adapter.register(Footer.class, new FooterViewProvider());

    rv.setAdapter(adapter);
    rv.addItemDecoration(new DividerListItemDecoration(this));
    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!noMoreReplies
            && newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          LogUtil.d(TAG, "add more: offset " + offset);
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_LOADING);
          adapter.notifyItemChanged(adapter.getItemCount());
          topicRepliesPresenter.addReplies(offset);
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });

    topicRepliesPresenter.setId(topicId);

    if (topicId != 0) {
      topicPresenter.getTopic(topicId);
    }

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String loginName = PrefUtil.getMe(TopicActivity.this).getLogin();
        if (TextUtils.isEmpty(loginName)) {
          signIn();
          return;
        }

        navigator.navigateToCreateTopicReplyActivity(TopicActivity.this, topicDetail.getId(), topicDetail.getTitle());
      }
    });
  }

  @Override
  public void showTopic(TopicDetail topicDetail) {
    items.add(topicDetail);
    this.topicDetail = topicDetail;
    adapter.notifyItemInserted(adapter.getItemCount());
    requestReplies();
  }

  @Override
  public void loadTopicFinish() {
    progressBar.setVisibility(View.GONE);
    rv.setVisibility(View.VISIBLE);
    fab.setVisibility(View.VISIBLE);
  }

  @Override
  public void showFavorite(boolean bool) {

  }

  @Override
  public void showUnFavorite(boolean bool) {

  }

  @Override
  public void showFollow(boolean bool) {

  }

  @Override
  public void showUnFollow(boolean bool) {

  }

  @Override
  public void showLike(boolean bool) {

  }

  @Override
  public void showUnLike(boolean bool) {

  }

  @Override
  public void showSignIn() {
    signIn();
  }

  private void requestReplies() {
    topicRepliesPresenter.getReplies();
  }

  @Override
  protected Toolbar getToolbar() {
    return toolbar;
  }

  @Override
  public void showReplies(List<TopicReply> topicReplyList) {
    if (topicReplyList != null) {
      for (TopicReply topicReply : topicReplyList) {
        items.add(new TopicReplyWithTopic(this.topicDetail, topicReply));
        adapter.notifyItemInserted(adapter.getItemCount());
      }
      offset = this.items.size() - 1;// 去除 Header
      switch (topicReplyList.size()) {
        case 20:
          noMoreReplies = false;
          items.add(new Footer(Footer.STATUS_NORMAL));
          adapter.notifyItemInserted(adapter.getItemCount());
          break;
        case 0:
          noMoreReplies = true;
          items.add(new Footer(Footer.STATUS_NO_MORE));
          adapter.notifyItemInserted(adapter.getItemCount());
          break;
        default:
          noMoreReplies = true;
          items.add(new Footer(Footer.STATUS_NO_MORE));
          adapter.notifyItemInserted(adapter.getItemCount());
          break;
      }
    }
  }

  @Override
  public void addReplies(List<TopicReply> topicReplyList) {
    if (topicReplyList != null) {
      for (TopicReply topicReply : topicReplyList) {
        // 插入 FooterView 前面
        items.add(items.size() - 1, new TopicReplyWithTopic(this.topicDetail, topicReply));
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
      }
      switch (topicReplyList.size()) {
        case 20:
          noMoreReplies = false;
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NORMAL);
          adapter.notifyItemChanged(adapter.getItemCount());
          break;
        case 0:
          noMoreReplies = true;
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
          adapter.notifyItemChanged(adapter.getItemCount());
          break;
        default:
          noMoreReplies = true;
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
          adapter.notifyItemChanged(adapter.getItemCount());
          break;
      }
      offset = this.items.size() - 2; // 去除 Footer & Header
    }
  }

  @Override
  public void showNewReply() {
    LogUtil.d(TAG, "add more: offset " + offset);
    ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_LOADING);
    adapter.notifyItemChanged(adapter.getItemCount());
    topicRepliesPresenter.addReplies(offset);
  }

  @Override protected void onPause() {
    // qualification : topicDetail != null
    // fixed bug : when loading data, swipe back will crush.
    if(topicDetail != null) {
      if (topicDetail.isFavorited()) {
        topicPresenter.favoriteTopic(topicId);
      } else {
        topicPresenter.unFavoriteTopic(topicId);
      }

      if (topicDetail.isLiked()) {
        topicPresenter.like(topicId);
      } else {
        topicPresenter.unLike(topicId);
      }
    } else {
      finish();
    }

    super.onPause();
  }

  private void signIn() {
    Snackbar.make(coordinator, "请先登录", Snackbar.LENGTH_LONG)
        .setAction("登录", new View.OnClickListener() {
          @Override public void onClick(View v) {
            startActivityForResult(SignInActivity.getCallingIntent(TopicActivity.this),
                SignInActivity.REQUEST_CODE);
          }
        })
        .show();
  }

  @Override
  protected void onStart() {
    LogUtil.v(TAG, "onStart");
    super.onStart();
    if (topicPresenter != null || topicRepliesPresenter != null) {
      topicRepliesPresenter.bind(this);
      topicPresenter.bind(this);
      topicRepliesPresenter.start();
      topicPresenter.start();
    }
  }

  @Override
  protected void onStop() {
    LogUtil.v(TAG, "onStop");
    if (topicPresenter != null || topicRepliesPresenter != null) {
      topicPresenter.unbind();
      topicRepliesPresenter.unbind();
      topicPresenter.stop();
      topicRepliesPresenter.stop();
    }
    super.onStop();
  }

  ITopicListener listener = new ITopicListener() {
    @Override
    public void TopicDetailListener(View view, String loginName) {
      navigator.navigateToUserActivity(view.getContext(), loginName);
    }

    @Override
    public void TopicReplyListenerToUser(Context context, String url) {
      navigator.navigateToUserActivity(context, url);
    }

    @Override
    public void TopicReplyListenerToWeb(Context context, String url) {
      navigator.navigateToWebActivity(context, url);
    }

    @Override
    public void TopicReplyListenerToCreateTopicReply(View view, int id, String title, String to) {

      navigator.navigateToCreateTopicReplyActivityMore(view.getContext(), id, title, to);
    }

    @Override
    public void TopicReplyListenerToUser2(View view, String loginName) {
      navigator.navigateToUserActivity(view.getContext(), loginName);
    }
  };
}
