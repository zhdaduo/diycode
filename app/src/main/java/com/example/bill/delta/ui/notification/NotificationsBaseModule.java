package com.example.bill.delta.ui.notification;

import com.example.bill.delta.api.NotificationService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.notification.NotificationsBaseMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class NotificationsBaseModule {

  NotificationsBaseMVP.View view;

  public NotificationsBaseModule(
      View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  NotificationService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(NotificationService.class);
  }

  @PerActivity
  @Provides
  NotificationsBaseMVP.Model provideModel(NotificationsBaseModel model) {
    return model;
  }

  @PerActivity
  @Provides
  NotificationsBaseMVP.Presenter providePresenter(NotificationsBasePresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
