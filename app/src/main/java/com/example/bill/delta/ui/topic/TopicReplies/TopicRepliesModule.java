package com.example.bill.delta.ui.topic.TopicReplies;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesMVP.View;
import dagger.Module;
import dagger.Provides;

@Module
public class TopicRepliesModule {

  TopicRepliesMVP.View view;

  public TopicRepliesModule(View view) {
    this.view = view;
  }

  /*@PerActivity
  @Provides
  TopicService provideTopicService(@Named("AuthRetrofit") Retrofit retrofit) {
    return retrofit.create(TopicService.class);
  }*/

  @Provides
  @PerActivity
  TopicRepliesMVP.Model provideModel(TopicRepliesModel model) {
    return model;
  }

  @Provides
  @PerActivity
  TopicRepliesMVP.Presenter providePresenter(TopicRepliesPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
