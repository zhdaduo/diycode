package com.example.bill.delta.ui.topic.Topic;

import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class TopicModule {

  TopicMVP.View view;

  public TopicModule(TopicMVP.View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  TopicService provideTopicService(@Named("AuthRetrofit") Retrofit retrofit) {
    return retrofit.create(TopicService.class);
  }

  @Provides
  @PerActivity
  TopicMVP.Model provideModel(TopicModel model) {
    return model;
  }

  @Provides
  @PerActivity
  public TopicMVP.Presenter providePresenter(TopicPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }

}
