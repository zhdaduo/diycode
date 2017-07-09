package com.example.bill.delta.view.adapter.topic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.bean.topic.event.LoadTopicDetailFinishEvent;
import com.example.bill.delta.bean.topic.event.SignInEvent;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.util.TimeUtil;
import com.example.bill.delta.view.Listener.ITopicListener;
import com.example.bill.delta.view.widget.DWebView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.multitype.ItemViewProvider;
import org.greenrobot.eventbus.EventBus;

public class TopicDetailViewProvider
    extends ItemViewProvider<TopicDetail, TopicDetailViewProvider.ViewHolder> {

  private static final String TAG = "TopicDetailViewProvider";
  private final ITopicListener onTopicDetailListener;

  public TopicDetailViewProvider(
      ITopicListener onTopicDetailListener) {
    this.onTopicDetailListener = onTopicDetailListener;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_topic_detail, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull final ViewHolder holder,
      @NonNull final TopicDetail topicDetail) {
    //item backgroundResource
    final int lastPosition = getAdapter().getItemCount() - 1;
    if (getAdapter().getItemCount() == 1) {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_single_background);
    } else if (getPosition(holder) == 0) {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_first_background);
    } else if (getPosition(holder) == lastPosition) {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_last_background);
    } else {
      holder.itemView.setBackgroundResource(R.drawable.item_feed_normal_background);
    }

    holder.name.setText(topicDetail.getUser().getLogin());
    holder.time.setText(TimeUtil.computePastTime(topicDetail.getUpdatedAt()));
    holder.title.setText(topicDetail.getTitle());

    Glide.with(holder.avatar.getContext())
        .load(topicDetail.getUser().getAvatarUrl())
        .bitmapTransform(new CropCircleTransformation(holder.avatar.getContext()))
        .placeholder(R.drawable.shape_glide_img_error)
        .error(R.drawable.shape_glide_img_error)
        .crossFade()
        .into(holder.avatar);

    holder.topic.setText(topicDetail.getNodeName());
    holder.repliesCount.setText("共收到 " + topicDetail.getRepliesCount() + " 条回复");
    updateLike(topicDetail, holder.like, holder.likeCount, false);

    holder.like.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateLike(topicDetail, holder.like, holder.likeCount, true);
      }
    });

    if (topicDetail.getLikesCount() > 0) {
      holder.likeCount.setText(topicDetail.getLikesCount() + "");
    }
    updateFavorite(topicDetail, holder.favorite, false);

    holder.favorite.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateFavorite(topicDetail, holder.favorite, true);
      }
    });

    holder.content.loadDetailDataAsync(topicDetail.getBodyHtml(), new Runnable() {
      @Override
      public void run() {
        EventBus.getDefault().post(new LoadTopicDetailFinishEvent());
      }
    });

    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onTopicDetailListener.TopicDetailListener(view, topicDetail.getUser().getLogin());
      }
    };

    holder.avatar.setOnClickListener(listener);
    holder.name.setOnClickListener(listener);
  }

  private void updateFavorite(TopicDetail topicDetail, ImageView imageView, boolean reverse) {
    String loginName = PrefUtil.getMe(imageView.getContext()).getLogin();
    if (TextUtils.isEmpty(loginName) && reverse) {
      EventBus.getDefault().post(new SignInEvent());
      return;
    }
    if (reverse) {
      topicDetail.setFavorited(!topicDetail.isFavorited());
    }
    if (topicDetail.isFavorited()) {
      //Genymotion Nexus 11 Android: 4.4.2
      //fixed bug : Resources$NotFoundException
      imageView.setBackground(imageView.getContext().getResources().getDrawable(R.drawable.ic_favorite));
      //imageView.setImageResource(R.drawable.ic_favorite);
    } else {
      //imageView.setImageResource(R.drawable.ic_favorite_not);
      imageView.setBackground(imageView.getContext().getResources().getDrawable(R.drawable.ic_favorite_not));
    }
  }

  private void updateLike(TopicDetail topicDetail, ImageView imageView, TextView textView,
      boolean click) {
    String loginName = PrefUtil.getMe(imageView.getContext()).getLogin();
    if (TextUtils.isEmpty(loginName) && click) {
      EventBus.getDefault().post(new SignInEvent());
      return;
    }

    if (click) {
      topicDetail.setLiked(!topicDetail.isLiked());
      if (topicDetail.isLiked()) {
        topicDetail.setLikesCount(topicDetail.getLikesCount() + 1);
        textView.setText(topicDetail.getLikesCount() + "");
      } else {
        topicDetail.setLikesCount(topicDetail.getLikesCount() - 1);
        if (topicDetail.getLikesCount() > 0) {
          textView.setText(topicDetail.getLikesCount() + "");
        } else if (topicDetail.getLikesCount() == 0) {
          textView.setText("");
        }
      }
    } else {
      if (topicDetail.getLikesCount() > 0) {
        textView.setText(topicDetail.getLikesCount() + "");
      }
    }
    if (topicDetail.isLiked()) {
      //imageView.setImageResource(R.drawable.ic_like);
      imageView.setBackground(imageView.getContext().getResources().getDrawable(R.drawable.ic_like));

    } else {
      //imageView.setImageResource(R.drawable.ic_like_not);
      imageView.setBackground(imageView.getContext().getResources().getDrawable(R.drawable.ic_like_not));
    }
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.content) DWebView content;
    @BindView(R.id.replies_count) TextView repliesCount;
    @BindView(R.id.favorite) ImageView favorite;
    @BindView(R.id.like_count) TextView likeCount;
    @BindView(R.id.like) ImageView like;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}