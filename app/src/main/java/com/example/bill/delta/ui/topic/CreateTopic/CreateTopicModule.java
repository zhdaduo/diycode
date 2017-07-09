package com.example.bill.delta.ui.topic.CreateTopic;

import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.topic.CreateTopic.CreateTopicMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class CreateTopicModule {

  CreateTopicMVP.View view;

  public CreateTopicModule(View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  TopicService provideTopicService(@Named("AuthRetrofit") Retrofit retrofit) {
    return retrofit.create(TopicService.class);
  }

  @Provides
  @PerActivity
  CreateTopicMVP.Model provideModel(CreateTopicModel model) {
    return model;
  }

  @Provides
  @PerActivity
  CreateTopicMVP.Presenter providePresenter(CreateTopicPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
