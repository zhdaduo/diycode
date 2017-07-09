package com.example.bill.delta.view.adapter.topic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.topic.event.SignInEvent;
import com.example.bill.delta.util.GlideImageGetter;
import com.example.bill.delta.util.HtmlUtil;
import com.example.bill.delta.util.LinkMovementMethodExt;
import com.example.bill.delta.util.PrefUtil;
import com.example.bill.delta.util.SpanClickListener;
import com.example.bill.delta.util.TimeUtil;
import com.example.bill.delta.view.Listener.ITopicListener;
import java.util.Locale;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.multitype.ItemViewProvider;
import org.greenrobot.eventbus.EventBus;

public class TopicReplyViewProvider
    extends ItemViewProvider<TopicReplyWithTopic, TopicReplyViewProvider.ViewHolder> {

  private static final String TAG = "TopicReplyViewProvider";
  private final ITopicListener listener;

  public TopicReplyViewProvider(ITopicListener listener) {
    this.listener = listener;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_topic_reply, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull final ViewHolder holder,
      @NonNull final TopicReplyWithTopic topicReply) {
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

    holder.name.setText(topicReply.getTopicReply().getUser().getLogin());
    String floor = String.format(Locale.CHINESE, "%d楼", getPosition(holder));
    holder.position.setText(floor);
    holder.time.setText(
        "  ·  " + TimeUtil.computePastTime(topicReply.getTopicReply().getUpdatedAt()));

    Glide.with(holder.avatar.getContext())
        .load(topicReply.getTopicReply().getUser().getAvatarUrl())
        .bitmapTransform(new CropCircleTransformation(holder.avatar.getContext()))
        .placeholder(R.drawable.shape_glide_img_error)
        .error(R.drawable.shape_glide_img_error)
        .crossFade()
        .into(holder.avatar);

    holder.content.setText(
        Html.fromHtml(HtmlUtil.removeP(topicReply.getTopicReply().getBodyHtml()),
            new GlideImageGetter(holder.content.getContext(), holder.content), null));

    holder.content.setMovementMethod(new LinkMovementMethodExt(new SpanClickListener() {
      @Override
      public void onClick(int type, String url) {
        Log.d(TAG, "url: " + url);
        if (url.startsWith("/")) {
          // url: "/plusend"
          Log.d(TAG, "username: " + url.substring(1));
          listener.TopicReplyListenerToUser(holder.content.getContext(), url.substring(1));
        } else if (url.startsWith("#")) {
          // url: "#reply1"
          Log.d(TAG, "楼");
          // TODO
        } else {
          listener.TopicReplyListenerToWeb(holder.content.getContext(), url);
        }
      }
    }));

    holder.reply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String loginName = PrefUtil.getMe(holder.reply.getContext()).getLogin();
        if (TextUtils.isEmpty(loginName)) {
          EventBus.getDefault().post(new SignInEvent());
          return;
        }
        listener.TopicReplyListenerToCreateTopicReply(view,
            topicReply.getTopicDetail().getId(),
            topicReply.getTopicDetail().getTitle(),
            "#" + holder.position.getText().toString() + " @" + topicReply.getTopicReply()
                .getUser().getLogin() + " ");
      }
    });

    holder.avatar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.TopicReplyListenerToUser2(v, topicReply.getTopicReply().getUser().getLogin());
      }
    });
    holder.name.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.TopicReplyListenerToUser2(v, topicReply.getTopicReply().getUser().getLogin());
      }
    });
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.position) TextView position;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.reply) ImageView reply;
    @BindView(R.id.content) TextView content;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}