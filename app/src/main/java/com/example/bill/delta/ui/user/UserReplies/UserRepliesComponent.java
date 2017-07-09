package com.example.bill.delta.ui.user.UserReplies;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = UserRepliesModule.class)
public interface UserRepliesComponent {

  void inject(MyUserRepliesActivity myUserRepliesActivity);
}
