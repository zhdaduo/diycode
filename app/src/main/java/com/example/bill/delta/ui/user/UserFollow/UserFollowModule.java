package com.example.bill.delta.ui.user.UserFollow;

import com.example.bill.delta.internal.di.scopes.PerFragment;
import com.example.bill.delta.ui.user.UserFollow.UserFollowMVP.View;
import dagger.Module;
import dagger.Provides;

@Module
public class UserFollowModule {

  UserFollowMVP.View view;

  public UserFollowModule(View view) {
    this.view = view;
  }

/*
  @PerFragment
  @Provides
  UserService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(UserService.class);
  }
*/

  @PerFragment
  @Provides
  UserFollowMVP.Model provideModel(UserFollowModel model){
    return model;
  }

  @PerFragment
  @Provides
  UserFollowMVP.Presenter providePresenter(UserFollowPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
