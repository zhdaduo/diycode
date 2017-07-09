package com.example.bill.delta.view.adapter.reply;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.notification.Notification.Reply;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.util.GlideImageGetter;
import com.example.bill.delta.util.HtmlUtil;
import com.example.bill.delta.util.TimeUtil;
import com.example.bill.delta.view.Listener.IReplyListener;
import javax.inject.Inject;
import me.drakeet.multitype.ItemViewProvider;

public class ReplyViewProvider extends ItemViewProvider<Reply, ReplyViewProvider.ViewHolder> {

  private static final String TAG = "ReplyViewProvider";
  private final IReplyListener onReplyListener;

  public ReplyViewProvider(IReplyListener onReplyListener) {
    this.onReplyListener = onReplyListener;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_reply, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Reply reply) {
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

    holder.reply = reply;
    holder.topicTitle.setText(Html.fromHtml("<font color='#ff0099cc'> 在帖子 </font>"
        + reply.getTopicTitle()
        + "<font color='#ff0099cc'> 发表了回复：</font>"));
    holder.body.setText(Html.fromHtml(HtmlUtil.removeP(reply.getBodyHtml()),
        new GlideImageGetter(holder.body.getContext(), holder.body), null));
    holder.time.setText(TimeUtil.formatTime(reply.getUpdatedAt()));

    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onReplyListener.ReplyListener(v, reply.getTopicId());
      }
    });
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.topic_title) TextView topicTitle;
    @BindView(R.id.body) TextView body;
    @BindView(R.id.time) TextView time;
    private Reply reply;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}