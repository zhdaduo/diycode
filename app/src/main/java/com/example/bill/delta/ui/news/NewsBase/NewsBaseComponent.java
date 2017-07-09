package com.example.bill.delta.ui.news.NewsBase;

import com.example.bill.delta.internal.di.scopes.PerFragment;
import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = NewsBaseModule.class)
public interface NewsBaseComponent {

  void inject(NewsFragment newsFragment);
}
