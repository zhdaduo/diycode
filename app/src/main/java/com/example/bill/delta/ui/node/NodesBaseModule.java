package com.example.bill.delta.ui.node;

import com.example.bill.delta.api.NodeService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.node.NodesBaseMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class NodesBaseModule {

  NodesBaseMVP.View view;

  public NodesBaseModule(View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  NodeService provideTopicService(@Named("Retrofit") Retrofit retrofit) {
    return retrofit.create(NodeService.class);
  }

  @Provides
  @PerActivity
  NodesBaseMVP.Model provideModel(NodesBaseModel model) {
    return model;
  }

  @Provides
  @PerActivity
  NodesBaseMVP.Presenter providePresenter(NodesBasePresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
