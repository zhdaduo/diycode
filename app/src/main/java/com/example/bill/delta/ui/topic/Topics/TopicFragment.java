package com.example.bill.delta.ui.topic.Topics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.topic.Topic;
import com.example.bill.delta.bean.user.UserInfo;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.base.BaseFragment;
import com.example.bill.delta.ui.user.UserFollow.UserFollowMVP;
import com.example.bill.delta.ui.user.UserFollow.UserFollowModule;
import com.example.bill.delta.ui.user.UserFollow.UserFollowPresenter;
import com.example.bill.delta.ui.user.UserTopics.UserTopicsModule;
import com.example.bill.delta.ui.user.UserTopics.UserTopicsPresenter;
import com.example.bill.delta.util.Constant;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.view.Listener.ITopicViewListener;
import com.example.bill.delta.view.Listener.IUserInfoListener;
import com.example.bill.delta.view.adapter.topic.Footer;
import com.example.bill.delta.view.adapter.topic.FooterViewProvider;
import com.example.bill.delta.view.adapter.topic.TopicViewProvider;
import com.example.bill.delta.view.adapter.user.UserInfoViewProvider;
import com.example.bill.delta.view.widget.DividerListItemDecoration;
import com.example.bill.delta.view.widget.EmptyRecyclerView;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class TopicFragment  extends BaseFragment implements TopicsMVP.View, UserFollowMVP.View {

  public static final String TYPE = "type";
  public static final int TYPE_ALL = 1;
  public static final int TYPE_CREATE = 2;
  public static final int TYPE_FAVORITE = 3;
  public static final int TYPE_USERFOLLOWING = 4;
  private static final String TAG = "TopicFragment";

  private MultiTypeAdapter adapter;
  private Items items;
  private LinearLayoutManager linearLayoutManager;
  private int offset = 0;
  private int type = 0;
  private String loginName;
  private boolean isFirstLoad = true;

  @BindView(R.id.rv) EmptyRecyclerView rv;
  @BindView(R.id.empty_view) ProgressBar empty_view;

  @Inject TopicsPresenter topicsPresenter;
  @Inject UserTopicsPresenter userTopicsPresenter;
  @Inject UserFollowPresenter userFollowPresenter;
  @Inject Navigator navigator;

  public static TopicFragment newInstance(String loginName, int type) {
    LogUtil.v(TAG, "newInstance type: " + type);
    TopicFragment topicFragment = new TopicFragment();
    Bundle b = new Bundle();
    b.putString(Constant.User.LOGIN, loginName);
    b.putInt(TYPE, type);
    topicFragment.setArguments(b);
    return topicFragment;
  }

  public TopicFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    LogUtil.v(TAG, "onCreate");
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getActivity().getApplication()).getApplicationComponent()
        .plus(new TopicsModule(this), new UserTopicsModule(this), new UserFollowModule(this))
        .inject(this);
    initAdapter();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    LogUtil.v(TAG, "onCreateView");
    LogUtil.v(TAG, "items.size: " + items.size() + " offset: " + offset + " type: " + type);
    View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
    ButterKnife.bind(this, rootView);
    initRV();
    return rootView;
  }

  private void initAdapter() {
    items = new Items();
    adapter = new MultiTypeAdapter(items);
    adapter.register(Topic.class, new TopicViewProvider(listener));
    adapter.register(UserInfo.class, new UserInfoViewProvider(userInfoListener));
    adapter.register(Footer.class, new FooterViewProvider());
  }

  private void initRV() {
    linearLayoutManager = new LinearLayoutManager(getContext());
    rv.setLayoutManager(linearLayoutManager);
    rv.setAdapter(adapter);
    rv.setEmptyView(empty_view);
    rv.addItemDecoration(new DividerListItemDecoration(getContext()));
    loadMore();
  }

  private void loadMore() {
    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && lastVisibleItem + 1 == adapter.getItemCount()) {
          ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_LOADING);
          adapter.notifyItemChanged(adapter.getItemCount());
          if (type == TYPE_ALL) {
            topicsPresenter.getTopics(offset);
          } else if (type == TYPE_CREATE) {
            userTopicsPresenter.getUserCreateTopics(loginName,
                offset);
          } else if (type == TYPE_FAVORITE) {
            userTopicsPresenter.getUserFavoriteTopics(loginName,
                offset);
          } else if (type == TYPE_USERFOLLOWING) {
            userFollowPresenter.getUserFollowing(loginName, offset);
          }
        }
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
      }
    });
  }

  @Override
  public void hideLoading() {
       empty_view.setVisibility(View.GONE);
       rv.setVisibility(View.VISIBLE);
  }

  @Override
  public void showTopics(List<Topic> topicList) {
    if (topicList == null) {
      LogUtil.v(TAG, "showTopics: null");
      return;
    }
    LogUtil.v(TAG, "showTopics: " + topicList.size());
    if (items.size() == 0) {
      items.add(new Footer(Footer.STATUS_NORMAL));
    }
    for (Topic topic : topicList) {
      // 插入 FooterView 前面
      items.add(items.size() - 1, topic);
      adapter.notifyItemInserted(adapter.getItemCount() - 1);
    }
    offset = items.size() - 1;
    if (topicList.size() < 20) {
      ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
    } else {
      ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NORMAL);
    }
    adapter.notifyItemChanged(adapter.getItemCount());
  }

  @Override
  public void showTopTopics(List<Topic> topicList) {
    if (topicList == null) {
      LogUtil.v(TAG, "showTopTopics: null");
      return;
    }
    LogUtil.v(TAG, "showTopTopics: " + topicList.size());
    int size = topicList.size();
    for (int i = 0; i < size; i++) {
      items.add(i, topicList.get(i));
    }
    adapter.notifyDataSetChanged();
  }

  @Override
  public void getUserFollowing(List<UserInfo> userInfoList) {
    if (userInfoList == null) {
      LogUtil.v(TAG, "getUserFollowing: null");
      return;
    }
    LogUtil.v(TAG, "getUserFollowing: " + userInfoList.size());
    if (items.size() == 0) {
      items.add(new Footer(Footer.STATUS_NORMAL));
    }
    for (UserInfo userInfo : userInfoList) {
      // 插入 FooterView 前面
      items.add(items.size() - 1, userInfo);
      adapter.notifyItemInserted(adapter.getItemCount() - 1);
    }
    offset = items.size() - 1;
    if (userInfoList.size() < 20) {
      ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NO_MORE);
    } else {
      ((Footer) items.get(items.size() - 1)).setStatus(Footer.STATUS_NORMAL);
    }
    adapter.notifyItemChanged(adapter.getItemCount());
  }

  @Override
  public void onStart() {
    LogUtil.v(TAG, "onStart");
    super.onStart();

    Bundle bundle = getArguments();
    if (bundle != null) {
      loginName = bundle.getString(Constant.User.LOGIN);
      type = bundle.getInt(TYPE);
      LogUtil.d(TAG, "loginName: " + loginName + " type: " + type);
    }

    if (type == TYPE_ALL && topicsPresenter != null) {
      topicsPresenter.bind(this);
      topicsPresenter.start();
    } else if (type == TYPE_CREATE && userTopicsPresenter != null) {
      userTopicsPresenter.bind(this);
      userTopicsPresenter.start();
    } else if (type == TYPE_FAVORITE && userTopicsPresenter != null) {
      userTopicsPresenter.bind(this);
      userTopicsPresenter.start();
    } else if (type == TYPE_USERFOLLOWING && userFollowPresenter != null) {
      userFollowPresenter.bind(this);
      userFollowPresenter.start();
    }

    LogUtil.v(TAG, "isFirstLoad: " + isFirstLoad);

      if (!TextUtils.isEmpty(loginName)) {
        if (type == TYPE_CREATE) {
          userTopicsPresenter.getUserCreateTopics(loginName,
              offset);
        } else if (type == TYPE_FAVORITE) {
          userTopicsPresenter.getUserFavoriteTopics(loginName,
              offset);
        } else if (type == TYPE_USERFOLLOWING) {
          userFollowPresenter.getUserFollowing(loginName, offset);
        }
    }
  }

  @Override
  public void onStop() {
    if (topicsPresenter != null || userTopicsPresenter != null || userFollowPresenter != null) {
      topicsPresenter.stop();
      userTopicsPresenter.stop();
      userFollowPresenter.stop();
      topicsPresenter.unbind();
      userTopicsPresenter.unbind();
      userFollowPresenter.unbind();
    }
    super.onStop();
  }

  @Override
  public void fetchData() {
    if (isFirstLoad) {
      // TODO 置顶帖子的获取
      topicsPresenter.getTopTopics();
      topicsPresenter.getTopics(offset);
      // 标记 Fragment 已经进行过第一次加载
      isFirstLoad = false;
    }
  }

  ITopicViewListener listener = new ITopicViewListener() {
    @Override
    public void TopicListener(View view, int id) {
      navigator.navigateToTopicActivity(view.getContext(), id);
    }
  };
  IUserInfoListener userInfoListener = new IUserInfoListener() {
    @Override
    public void UserInfoListener(View view, String loginName) {
      if (PrefUtil.getMe(getContext()).getLogin().equals(loginName)) {
        navigator.navigateToUserActivity(view.getContext(), loginName, true);
      } else {
        navigator.navigateToUserActivity(view.getContext(), loginName, false);
      }
    }
  };
}
