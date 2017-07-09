package com.example.bill.delta.view.adapter.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.bill.delta.R;
import com.example.bill.delta.view.Listener.INotificationListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.multitype.ItemViewProvider;

public class NotificationFollowViewProvider
    extends ItemViewProvider<NotificationFollow, NotificationFollowViewProvider.ViewHolder> {

  private final INotificationListener onFollowListener;

  public NotificationFollowViewProvider(
      INotificationListener onFollowListener) {
    this.onFollowListener = onFollowListener;
  }

  @NonNull
  @Override
  protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_notification_follow, parent, false);
    return new ViewHolder(root);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder,
      @NonNull final NotificationFollow notificationFollow) {
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

    Glide.with(holder.avatar.getContext())
        .load(notificationFollow.getAvatarUrl())
        .bitmapTransform(new CropCircleTransformation(holder.avatar.getContext()))
        .placeholder(R.drawable.shape_glide_img_error)
        .error(R.drawable.shape_glide_img_error)
        .crossFade()
        .into(holder.avatar);

    holder.login.setText(notificationFollow.getLogin());

    View.OnClickListener onClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onFollowListener.FollowListener(v, notificationFollow.getLogin());
      }
    };
    holder.avatar.setOnClickListener(onClickListener);
    holder.login.setOnClickListener(onClickListener);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.login) TextView login;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}