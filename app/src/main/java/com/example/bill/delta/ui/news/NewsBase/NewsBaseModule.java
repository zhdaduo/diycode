package com.example.bill.delta.ui.news.NewsBase;

import com.example.bill.delta.api.NewsService;
import com.example.bill.delta.internal.di.scopes.PerFragment;
import com.example.bill.delta.ui.news.NewsBase.NewsBaseMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class NewsBaseModule {

  NewsBaseMVP.View view;

  public NewsBaseModule(View view) {
    this.view = view;
  }

  @PerFragment
  @Provides
  NewsService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(NewsService.class);
  }

  @PerFragment
  @Provides
  NewsBaseMVP.Model provideModel(NewsBaseModel model) {
    return model;
  }

  @PerFragment
  @Provides
  NewsBaseMVP.Presenter providePresenter(NewsBasePresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
