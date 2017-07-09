package com.example.bill.delta.ui.topic.CreateTopicReply;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = CreateTopicReplyModule.class)
public interface CreateTopicReplyComponent {

  void inject(CreateTopicReplyActivity createTopicReplyActivity);
}
