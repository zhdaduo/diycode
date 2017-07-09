package com.example.bill.delta.ui.user.UserReplies;

import com.example.bill.delta.api.UserService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.user.UserReplies.UserRepliesMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class UserRepliesModule {

  UserRepliesMVP.View view;

  public UserRepliesModule(View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  UserService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(UserService.class);
  }

  @PerActivity
  @Provides
  UserRepliesMVP.Model provideModel(UserRepliesModel model) {
    return model;
  }

  @PerActivity
  @Provides
  UserRepliesMVP.Presenter providePresenter(UserRepliesPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
