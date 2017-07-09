package com.example.bill.delta.ui.site;

import com.example.bill.delta.api.SiteService;
import com.example.bill.delta.internal.di.scopes.PerFragment;
import com.example.bill.delta.ui.site.SiteBaseMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class SiteBaseModule {

  SiteBaseMVP.View view;

  public SiteBaseModule(View view) {
    this.view = view;
  }

  @PerFragment
  @Provides
  SiteService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(SiteService.class);
  }

  @Provides
  @PerFragment
  SiteBaseMVP.Model provideModel(SiteBaseModel model) {
    return model;
  }

  @Provides
  @PerFragment
  SiteBaseMVP.Presenter providePresenter(SiteBasePresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
