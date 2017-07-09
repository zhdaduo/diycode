package com.example.bill.delta.ui.user.SignIn;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.user.User.UserModule;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = { SignInModule.class, UserModule.class})
public interface SignInComponent {

  void inject(SignInActivity signInActivity);
}
