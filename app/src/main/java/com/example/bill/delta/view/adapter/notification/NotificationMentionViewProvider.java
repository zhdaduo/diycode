package com.example.bill.delta.view.adapter.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.example.bill.delta.util.GlideImageGetter;
import com.example.bill.delta.util.HtmlUtil;
import com.example.bill.delta.view.Listener.INotificationListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.multitype.ItemViewProvider;

public class NotificationMentionViewProvider
    extends ItemViewProvider<NotificationMention, NotificationMentionViewProvider.ViewHolder> {

  private final INotificationListener onMentionListener;

  public NotificationMentionViewProvider(
      INotificationListener onMentionListener) {
    this.onMentionListener = onMentionListener;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_notification_mention, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder,
      @NonNull final NotificationMention notificationMention) {
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

    holder.mention = notificationMention;

    Glide.with(holder.avatar.getContext())
        .load(notificationMention.getAvatarUrl().replace("large_", ""))
        .bitmapTransform(new CropCircleTransformation(holder.avatar.getContext()))
        .placeholder(R.drawable.shape_glide_img_error)
        .error(R.drawable.shape_glide_img_error)
        .crossFade()
        .into(holder.avatar);

    String header = notificationMention.getLogin()
        + "<font color='#9e9e9e'> 在 </font>"
        + notificationMention.getTopicTitle()
        + "<font color='#9e9e9e'> 提及你：</font>";

    holder.header.setText(Html.fromHtml(header));
    holder.body.setText(Html.fromHtml(HtmlUtil.removeP(notificationMention.getBodyHtml()),
        new GlideImageGetter(holder.body.getContext(), holder.body), null));

    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onMentionListener.MentionListener(v, notificationMention.getTopicId());
      }
    });
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.header) TextView header;
    @BindView(R.id.body) TextView body;

    private NotificationMention mention;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}