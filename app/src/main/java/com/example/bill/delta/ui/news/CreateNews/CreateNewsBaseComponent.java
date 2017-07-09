package com.example.bill.delta.ui.news.CreateNews;

import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseModule;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = { CreateNewsBaseModule.class, NewsNodesBaseModule.class})
public interface CreateNewsBaseComponent {

  void inject(CreateNewsActivity createNewsActivity);
}
