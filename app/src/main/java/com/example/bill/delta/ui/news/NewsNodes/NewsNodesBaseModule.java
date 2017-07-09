package com.example.bill.delta.ui.news.NewsNodes;

import com.example.bill.delta.api.NewsNodeService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.news.NewsBase.NewsBasePresenter;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class NewsNodesBaseModule {

  NewsNodesBaseMVP.View view;

  public NewsNodesBaseModule(View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  NewsNodeService provideTopicService (@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(NewsNodeService.class);
  }

  @PerActivity
  @Provides
  NewsNodesBaseMVP.Model provideModel(NewsNodesBaseModel model) {
    return model;
  }

  @PerActivity
  @Provides
  NewsNodesBaseMVP.Presenter providePresenter(NewsNodesBasePresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
