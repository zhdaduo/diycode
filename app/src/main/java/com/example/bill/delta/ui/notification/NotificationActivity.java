package com.example.bill.delta.ui.notification;

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
import com.example.bill.delta.bean.notification.Notification;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.base.BaseActivity;
import com.example.bill.delta.ui.user.SignIn.SignInActivity;
import com.example.bill.delta.util.Constant;
import com.example.bill.delta.view.Listener.INotificationListener;
import com.example.bill.delta.view.adapter.notification.NotificationElse;
import com.example.bill.delta.view.adapter.notification.NotificationElseViewProvider;
import com.example.bill.delta.view.adapter.notification.NotificationFollow;
import com.example.bill.delta.view.adapter.notification.NotificationFollowViewProvider;
import com.example.bill.delta.view.adapter.notification.NotificationMention;
import com.example.bill.delta.view.adapter.notification.NotificationMentionViewProvider;
import com.example.bill.delta.view.adapter.notification.NotificationReply;
import com.example.bill.delta.view.adapter.notification.NotificationReplyViewProvider;
import com.example.bill.delta.view.widget.DividerListItemDecoration;
import com.example.bill.delta.view.widget.EmptyRecyclerView;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class NotificationActivity extends BaseActivity implements NotificationsBaseMVP.View {

  private static final String TYPE_REPLY = "TopicReply";
  private static final String TYPE_MENTION = "Mention";
  private static final String TYPE_FOLLOW = "Follow";
  private static final String TYPE_NODE_CHANGED = "NodeChanged";
  private static final String MENTION_TYPE_NEWS = "HacknewsReply";
  private static final String MENTION_TYPE_REPLY = "Reply";

  private MultiTypeAdapter adapter;
  private LinearLayoutManager linearLayoutManager;
  private Items items;
  private int offset;

  @BindView(R.id.activity_notification) EmptyRecyclerView recyclerView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.empty_view) TextView emptyView;

  @Inject NotificationsBasePresenter notificationsBasePresenter;
  @Inject Navigator navigator;

  public static Intent getCallingIntent(Context context) {
    Intent callingIntent = new Intent(context, NotificationActivity.class);
    return callingIntent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_notification);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getApplication()).getApplicationComponent()
        .plus(new NotificationsBaseModule(this))
        .inject(this);

    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(NotificationReply.class, new NotificationReplyViewProvider(listener));
    adapter.register(NotificationMention.class, new NotificationMentionViewProvider(listener));
    adapter.register(NotificationFollow.class, new NotificationFollowViewProvider(listener));
    adapter.register(NotificationElse.class, new NotificationElseViewProvider());
    linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(adapter);
    recyclerView.setEmptyView(emptyView);
    recyclerView.addItemDecoration(new DividerListItemDecoration(getContext()));
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          //adapter.setStatus(TopicsAdapter.STATUS_LOADING);
          //adapter.notifyDataSetChanged();
          notificationsBasePresenter.readNotifications(offset);
        }
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });

    if (TextUtils.isEmpty(Constant.VALUE_TOKEN)) {
      startActivityForResult(SignInActivity.getCallingIntent(NotificationActivity.this),
          SignInActivity.REQUEST_CODE);
      this.showToastMessage("请先登录");
    } else {
      getNotifications();
    }
  }

  @Override
  public void showNotifications(List<Notification> notificationList) {
    if (notificationList == null || notificationList.isEmpty()) {
      return;
    }

    for (Notification notification : notificationList) {
      if (TYPE_REPLY.equals(notification.getType())) {
        NotificationReply reply =
            new NotificationReply(notification.getActor().getAvatarUrl(),
                notification.getActor().getLogin(), notification.getReply().getTopicTitle(),
                notification.getReply().getBodyHtml(),
                notification.getReply().getTopicId());
        items.add(reply);
      } else if (TYPE_MENTION.equals(notification.getType())) {
        if (MENTION_TYPE_REPLY.equals(notification.getMentionType())) {
          NotificationMention mention =
              new NotificationMention(notification.getActor().getAvatarUrl(),
                  notification.getActor().getLogin(),
                  String.valueOf(notification.getMention().getId()),
                  notification.getMention().getBodyHtml(),
                  notification.getMention().getTopicId());
          items.add(mention);
        } else if (MENTION_TYPE_NEWS.equals(notification.getMentionType())) {
          // 这里的 api 有个 bug：这种情况下没有返回 mention 的值
          NotificationMention mention =
              new NotificationMention(notification.getActor().getAvatarUrl(),
                  notification.getActor().getLogin(), "HacknewsReply", "提到了你", 411);
          items.add(mention);
        }
      } else if (TYPE_FOLLOW.equals(notification.getType())) {
        NotificationFollow follow =
            new NotificationFollow(notification.getActor().getAvatarUrl(),
                notification.getActor().getLogin());
        items.add(follow);
      } else {
        NotificationElse notificationElse = new NotificationElse(notification.getType());
        items.add(notificationElse);
      }
    }
    offset = items.size();
    adapter.notifyDataSetChanged();
  }

  @Override
  protected Toolbar getToolbar() {
    return toolbar;
  }

  private void getNotifications() {
    notificationsBasePresenter.readNotifications(offset);
  }


  public Context getContext() {
    return this;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case SignInActivity.REQUEST_CODE:
        if (resultCode == SignInActivity.RESULT_OK) {
          getNotifications();
        } else {
          this.showToastMessage("放弃登录");
          finish();
        }
        break;
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (notificationsBasePresenter != null) {
      notificationsBasePresenter.bind(this);
      notificationsBasePresenter.start();
    }
  }

  @Override
  protected void onStop() {
    if (notificationsBasePresenter != null) {
      notificationsBasePresenter.unbind();
      notificationsBasePresenter.stop();
    }
    super.onStop();
  }

  INotificationListener listener = new INotificationListener() {
    @Override
    public void FollowListener(View view, String loginName) {
      navigator.navigateToUserActivity(view.getContext(), loginName);
    }

    @Override
    public void MentionListener(View view, int id) {
      navigator.navigateToTopicActivity(view.getContext(), id);
    }

    @Override
    public void NotiReplyListener(View view, int id) {
      navigator.navigateToTopicActivity(view.getContext(), id);
    }
  };
}
