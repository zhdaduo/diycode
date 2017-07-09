package com.example.bill.delta.ui.notification;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = NotificationsBaseModule.class)
public interface NotificationsBaseComponent {

  void inject(NotificationActivity notificationActivity);
}
