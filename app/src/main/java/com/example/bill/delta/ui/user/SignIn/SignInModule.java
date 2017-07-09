package com.example.bill.delta.ui.user.SignIn;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.user.SignIn.SignInMVP.View;
import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

  SignInMVP.View view;

  public SignInModule(View view) {
    this.view = view;
  }

 /* @PerActivity
  @Provides
  UserService provideTopicService(@Named("AuthRetrofit") Retrofit retrofit) {
    return retrofit.create(UserService.class);
  }*/

  @PerActivity
  @Provides
  SignInMVP.Model provideModel(SignInModel model) {
    return model;
  }

  @PerActivity
  @Provides
  SignInMVP.Presenter providePresenter(SignInPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
