package com.example.bill.delta.ui.user.UserTopics;

import com.example.bill.delta.api.UserService;
import com.example.bill.delta.internal.di.scopes.PerFragment;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP;
import com.example.bill.delta.ui.topic.Topics.TopicsMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class UserTopicsModule {

  TopicsMVP.View view;

  public UserTopicsModule(View view) {
    this.view = view;
  }

  @PerFragment
  @Provides
  UserService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(UserService.class);
  }

  @PerFragment
  @Provides
  UserTopicsMVP.Model provideModel(UserTopicsModel model) {
    return model;
  }

  @PerFragment
  @Provides
  UserTopicsMVP.Presenter providePresenter (UserTopicsPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
