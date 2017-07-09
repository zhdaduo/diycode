package com.example.bill.delta.view.adapter.topic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.topic.Topic;
import com.example.bill.delta.util.TimeUtil;
import com.example.bill.delta.view.Listener.ITopicViewListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.multitype.ItemViewProvider;

public class TopicViewProvider extends ItemViewProvider<Topic, TopicViewProvider.ViewHolder> {

  private final ITopicViewListener onTopicListener;

  public TopicViewProvider(ITopicViewListener onTopicListener) {
    this.onTopicListener = onTopicListener;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_topic, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Topic topic) {
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

    holder.name.setText(topic.getUser().getLogin());
    holder.topic.setText(topic.getNodeName());
    holder.title.setText(topic.getTitle());
    if (topic.getRepliedAt() != null) {
      holder.time.setText(TimeUtil.computePastTime(topic.getRepliedAt()));
    } else {
      holder.time.setText(TimeUtil.computePastTime(topic.getCreatedAt()));
    }
    if (topic.isPin()) {
      holder.pin.setVisibility(View.VISIBLE);
    } else {
      holder.pin.setVisibility(View.GONE);
    }

    Glide.with(holder.avatar.getContext())
        .load(topic.getUser().getAvatarUrl())
        .bitmapTransform(new CropCircleTransformation(holder.avatar.getContext()))
        .placeholder(R.drawable.shape_glide_img_error)
        .error(R.drawable.shape_glide_img_error)
        .crossFade()
        .into(holder.avatar);

    holder.itemTopic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onTopicListener.TopicListener(v, topic.getId());
      }
    });
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_topic) RelativeLayout itemTopic;
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.topic) TextView topic;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.pin) TextView pin;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}