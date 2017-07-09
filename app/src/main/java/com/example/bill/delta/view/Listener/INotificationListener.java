package com.example.bill.delta.view.Listener;

import android.view.View;

/**
 * Listener Interface for
 * {@link com.example.bill.delta.view.adapter.notification.NotificationFollowViewProvider}.
 * {@link com.example.bill.delta.view.adapter.notification.NotificationMentionViewProvider}.
 * {@link com.example.bill.delta.view.adapter.notification.NotificationReplyViewProvider}.
 */

public interface INotificationListener {

  void FollowListener(View view, String loginName);

  void MentionListener(View view, int id);

  void NotiReplyListener(View view, int id);
}
