package com.example.bill.delta.ui.topic.Topic;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesModule;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = { TopicModule.class, TopicRepliesModule.class })
public interface TopicComponent {

  void inject(TopicActivity topicActivity);
}
