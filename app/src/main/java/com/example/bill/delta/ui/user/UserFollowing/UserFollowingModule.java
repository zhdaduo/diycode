package com.example.bill.delta.ui.user.UserFollowing;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class UserFollowingModule {

  UserFollowingMVP.View view;

  public UserFollowingModule(UserFollowingMVP.View view) {
    this.view = view;
  }

  @Provides
  @PerActivity
  UserFollowingMVP.Model provideModel(UserFollowingModel model) {
    return model;
  }

  @Provides
  @PerActivity
  public UserFollowingMVP.Presenter providePresenter(UserFollowingPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
