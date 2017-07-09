package com.example.bill.delta.ui.topic.Topics;

import com.example.bill.delta.internal.di.scopes.PerFragment;
import com.example.bill.delta.ui.user.UserFollow.UserFollowModule;
import com.example.bill.delta.ui.user.UserTopics.UserTopicsModule;
import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = { TopicsModule.class, UserTopicsModule.class, UserFollowModule.class})
public interface TopicsComponent {

  void inject(TopicFragment topicFragment);
}
