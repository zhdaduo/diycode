package com.example.bill.delta.ui.user.User;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.main.MainActivity;
import com.example.bill.delta.ui.user.UserFollow.UserFollowModule;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingModule;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = { UserModule.class, UserFollowingModule.class})
public interface UserComponent {

  void inject(UserActivity userActivity);
  void inject(MainActivity mainActivity);
}
