package com.example.bill.delta.ui.topic.CreateTopic;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.node.NodesBaseModule;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = { CreateTopicModule.class, NodesBaseModule.class })
public interface CreateTopicComponent {

  void inject(CreateTopicActivity createTopicActivity);
}
