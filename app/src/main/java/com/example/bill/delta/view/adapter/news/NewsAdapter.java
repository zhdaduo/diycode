package com.example.bill.delta.view.adapter.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.news.News;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.util.TimeUtil;
import com.example.bill.delta.util.UrlUtil;
import com.example.bill.delta.view.Listener.INewsListener;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final String TAG = "NewsAdapter";
  public static final int STATUS_NORMAL = 1;  //normal
  public static final int STATUS_LOADING = 2; //loading
  public static final int STATUS_NO_MORE = 3; //no more
  private static final int ITEM_NORMAL = 1;
  private static final int ITEM_FOOTER = 2;
  private int status;
  private List<News> newsList;

  private final INewsListener onNewsListener;

  public NewsAdapter(List<News> newsList, INewsListener onNewsListener) {
    this.newsList = newsList;
    this.onNewsListener = onNewsListener;
  }

  @Override
  public int getItemViewType(int position) {
    if (position + 1 == getItemCount()) {
      return ITEM_FOOTER;
    } else {
      return ITEM_NORMAL;
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case ITEM_NORMAL:
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_news, parent, false));
      case ITEM_FOOTER:
        return new FooterViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_topic_footer, parent, false));
      default:
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_news, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    //item backgroundResource
    final int lastPosition = getItemCount() - 1;
    if (getItemCount() == 1) {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_single_background);
    } else if (position == 0) {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_first_background);
    } else if (position == lastPosition) {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_last_background);
    } else {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_normal_background);
    }
    if (holder instanceof NewsViewHolder) {
      NewsViewHolder topicViewHolder = (NewsViewHolder) holder;
      topicViewHolder.news = newsList.get(position);
      topicViewHolder.name.setText(newsList.get(position).getUser().getLogin());
      topicViewHolder.topic.setText(newsList.get(position).getNodeName());
      topicViewHolder.title.setText(newsList.get(position).getTitle());
      topicViewHolder.itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          onNewsListener.NewsListener(v, newsList.get(position).getAddress());
        }
      });
      if (newsList.get(position).getRepliedAt() != null) {
        topicViewHolder.time.setText(
            TimeUtil.computePastTime(newsList.get(position).getUpdatedAt()));
      } else {
        topicViewHolder.time.setText(
            TimeUtil.computePastTime(newsList.get(position).getCreatedAt()));
      }
      topicViewHolder.host.setText(UrlUtil.getHost(newsList.get(position).getAddress()));
      Glide.with(topicViewHolder.avatar.getContext())
          .load(newsList.get(position).getUser().getAvatarUrl())
          .bitmapTransform(new CropCircleTransformation(topicViewHolder.avatar.getContext()))
          .placeholder(R.drawable.shape_glide_img_error)
          .error(R.drawable.shape_glide_img_error)
          .crossFade()
          .into(topicViewHolder.avatar);
    } else if (holder instanceof FooterViewHolder) {
      FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
      switch (status) {
        case STATUS_NORMAL:
          footerViewHolder.tips.setText("上拉加载更多");
          //footerViewHolder.progressBar.setVisibility(View.GONE);
          break;
        case STATUS_LOADING:
          footerViewHolder.tips.setText("正在加载更多...");
          break;
        case STATUS_NO_MORE:
          footerViewHolder.tips.setText("已经到底啦");
          break;
        default:
          break;
      }
    }
  }

  @Override
  public int getItemCount() {
    if (newsList == null || newsList.isEmpty()) {
      return 0;
    } else {
      return newsList.size() + 1;
    }
  }

  public void setStatus(int status) {
    this.status = status;
  }

  static class NewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.host) TextView host;

    private News news;

    @Inject Navigator navigator;

    NewsViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  static class FooterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tips) TextView tips;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    FooterViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
