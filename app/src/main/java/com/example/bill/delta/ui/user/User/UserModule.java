package com.example.bill.delta.ui.user.User;

import com.example.bill.delta.api.UserService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.user.User.UserMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class UserModule {

  UserMVP.View view;

  public UserModule(View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  UserService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(UserService.class);
  }

  @PerActivity
  @Provides
  UserMVP.Model provideModel(UserModel model) {
    return model;
  }

  @PerActivity
  @Provides
  UserMVP.Presenter providePresenter(UserPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
