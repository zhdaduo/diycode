package com.example.bill.delta.ui.news.CreateNews;

import com.example.bill.delta.api.NewsService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.news.CreateNews.CreateNewsBaseMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class CreateNewsBaseModule {

  CreateNewsBaseMVP.View view;

  public CreateNewsBaseModule(View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  NewsService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(NewsService.class);
  }

  @PerActivity
  @Provides
  CreateNewsBaseMVP.Model provideModel(CreateNewsBaseModel model) {
    return model;
  }

  @PerActivity
  @Provides
  CreateNewsBaseMVP.Presenter providePresenter(CreateNewsBasePresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
