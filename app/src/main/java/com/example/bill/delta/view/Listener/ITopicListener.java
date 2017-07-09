package com.example.bill.delta.view.Listener;

import android.content.Context;
import android.view.View;

/**
 * Listener Interface for
 * {@link com.example.bill.delta.view.adapter.topic.TopicDetailViewProvider}.
 * {@link com.example.bill.delta.view.adapter.topic.TopicReplyViewProvider}.
 */

public interface ITopicListener {

  void TopicDetailListener(View view, String loginName);

  void TopicReplyListenerToUser(Context context, String url);

  void TopicReplyListenerToWeb(Context context, String url);

  void TopicReplyListenerToCreateTopicReply(View view, int id, String title, String to);

  void TopicReplyListenerToUser2(View view, String loginName);

}
