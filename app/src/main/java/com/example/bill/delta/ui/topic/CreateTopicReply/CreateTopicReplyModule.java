package com.example.bill.delta.ui.topic.CreateTopicReply;

import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.internal.di.scopes.PerActivity;
import com.example.bill.delta.ui.topic.CreateTopicReply.CreateTopicReplyMVP.View;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import retrofit2.Retrofit;

@Module
public class CreateTopicReplyModule {

  CreateTopicReplyMVP.View view;

  public CreateTopicReplyModule(
      View view) {
    this.view = view;
  }

  @PerActivity
  @Provides
  TopicService provideTopicService(@Named("AuthRetrofit") Retrofit retrofit) {
    return retrofit.create(TopicService.class);
  }

  @Provides
  @PerActivity
  CreateTopicReplyMVP.Model provideModel(CreateTopicReplyModel model) {
    return model;
  }

  @Provides
  @PerActivity
  public CreateTopicReplyMVP.Presenter providePresenter(CreateTopicReplyPresenter presenter) {
    presenter.bind(view);
    return presenter;
  }
}
