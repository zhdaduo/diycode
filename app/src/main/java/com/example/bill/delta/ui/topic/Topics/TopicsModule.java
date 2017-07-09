package com.example.bill.delta.ui.topic.Topics;

import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.internal.di.scopes.PerFragment;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class TopicsModule {

  TopicsMVP.View view;

  public TopicsModule(View view) {
    this.view = view;
  }

  @PerFragment
  @Provides
  TopicService provideTopicService(@Named("AuthRetrofit") Retrofit retrofit) {
    return retrofit.create(TopicService.class);
  }

  @PerFragment
  @Provides
  TopicsMVP.Model provideModel(TopicsModel model) {
    return model;
  }

  @PerFragment
  @Provides
  TopicsMVP.Presenter providePresenter(TopicsPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
