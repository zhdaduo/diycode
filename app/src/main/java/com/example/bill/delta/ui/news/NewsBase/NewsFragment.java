package com.example.bill.delta.ui.news.NewsBase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.view.Listener.INewsListener;
import com.example.bill.delta.view.adapter.news.NewsAdapter;
import com.example.bill.delta.view.widget.DividerListItemDecoration;
import com.example.bill.delta.view.widget.EmptyRecyclerView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class NewsFragment extends Fragment implements NewsBaseMVP.View {

  private static final String TAG = "NewsFragment";

  private List<News> newsList = new ArrayList<>();
  private NewsAdapter newsAdapter;
  private LinearLayoutManager linearLayoutManager;
  private int offset;
  private boolean isFirstLoad = true;

  @BindView(R.id.rv_news) EmptyRecyclerView rv;
  @BindView(R.id.empty_view) TextView emptyView;

  @Inject NewsBasePresenter newsBasePresenter;
  @Inject Navigator navigator;

  public NewsFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getActivity().getApplication()).getApplicationComponent()
        .plus(new NewsBaseModule(this))
        .inject(this);

    newsAdapter = new NewsAdapter(newsList, listener);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    LogUtil.d(TAG, "onCreateView");
      View rootView = inflater.inflate(R.layout.fragment_news, container, false);
      ButterKnife.bind(this, rootView);
      linearLayoutManager = new LinearLayoutManager(this.getContext());
      rv.setLayoutManager(linearLayoutManager);
      rv.setEmptyView(emptyView);
      rv.setAdapter(newsAdapter);
      rv.addItemDecoration(new DividerListItemDecoration(getContext()));
      rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
          if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == newsAdapter
              .getItemCount()) {
            newsAdapter.setStatus(NewsAdapter.STATUS_LOADING);
            newsAdapter.notifyDataSetChanged();
            newsBasePresenter.readNews(offset);
          }
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }
      });
      return rootView;
  }

  @Override
  public void showNews(List<News> newsList) {
    LogUtil.v(TAG, "newsList: " + newsList);
    if (newsList == null) {
      return;
    }
    this.newsList.addAll(newsList);
    offset = this.newsList.size();
    if (newsList.isEmpty()) {
      newsAdapter.setStatus(NewsAdapter.STATUS_NO_MORE);
    } else {
      newsAdapter.setStatus(NewsAdapter.STATUS_NORMAL);
    }
    newsAdapter.notifyDataSetChanged();
  }

  @Override public void onStart() {
    super.onStart();
    if (newsBasePresenter != null) {
      newsBasePresenter.bind(this);
      newsBasePresenter.start();
      if (isFirstLoad) {
        newsBasePresenter.readNews(offset);
        isFirstLoad = false;
      }
    }
  }

  @Override public void onStop() {
    if (newsBasePresenter != null) {
      newsBasePresenter.unbind();
      newsBasePresenter.stop();
    }
    super.onStop();
  }

  INewsListener listener = new INewsListener() {
    @Override
    public void NewsListener(View view, String url) {
      navigator.navigateToWebActivity(view.getContext(), url);
    }
  };
}
