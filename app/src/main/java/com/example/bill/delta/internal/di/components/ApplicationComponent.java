package com.example.bill.delta.internal.di.components;

import com.example.bill.delta.internal.di.modules.ApplicationModule;
import com.example.bill.delta.ui.news.CreateNews.CreateNewsBaseComponent;
import com.example.bill.delta.ui.news.CreateNews.CreateNewsBaseModule;
import com.example.bill.delta.ui.news.NewsBase.NewsBaseComponent;
import com.example.bill.delta.ui.news.NewsBase.NewsBaseModule;
import com.example.bill.delta.ui.news.NewsNodes.NewsNodesBaseModule;
import com.example.bill.delta.ui.node.NodesBaseModule;
import com.example.bill.delta.ui.notification.NotificationsBaseComponent;
import com.example.bill.delta.ui.notification.NotificationsBaseModule;
import com.example.bill.delta.ui.site.SiteBaseComponent;
import com.example.bill.delta.ui.site.SiteBaseModule;
import com.example.bill.delta.ui.topic.CreateTopic.CreateTopicComponent;
import com.example.bill.delta.ui.topic.CreateTopic.CreateTopicModule;
import com.example.bill.delta.ui.topic.CreateTopicReply.CreateTopicReplyComponent;
import com.example.bill.delta.ui.topic.CreateTopicReply.CreateTopicReplyModule;
import com.example.bill.delta.ui.topic.Topic.TopicComponent;
import com.example.bill.delta.ui.topic.Topic.TopicModule;
import com.example.bill.delta.ui.topic.TopicReplies.TopicRepliesModule;
import com.example.bill.delta.ui.topic.Topics.TopicsComponent;
import com.example.bill.delta.ui.topic.Topics.TopicsModule;
import com.example.bill.delta.ui.user.SignIn.SignInComponent;
import com.example.bill.delta.ui.user.SignIn.SignInModule;
import com.example.bill.delta.ui.user.User.UserComponent;
import com.example.bill.delta.ui.user.User.UserModule;
import com.example.bill.delta.ui.user.UserFollow.UserFollowModule;
import com.example.bill.delta.ui.user.UserFollowing.UserFollowingModule;
import com.example.bill.delta.ui.user.UserReplies.UserRepliesComponent;
import com.example.bill.delta.ui.user.UserReplies.UserRepliesModule;
import com.example.bill.delta.ui.user.UserTopics.UserTopicsModule;
import com.example.bill.delta.view.activity.AboutActivity;
import com.example.bill.delta.view.activity.SplashActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(AboutActivity aboutActivity);
  void inject(SplashActivity splashActivity);

  TopicComponent plus(TopicModule topicModule, TopicRepliesModule topicRepliesModule);
  TopicsComponent plus(TopicsModule topicsModule, UserTopicsModule userTopicsModule, UserFollowModule userFollowModule);
  CreateTopicComponent plus(CreateTopicModule createTopicModule, NodesBaseModule nodesBaseModule);
  CreateTopicReplyComponent plus(CreateTopicReplyModule createTopicReplyModule);
  UserRepliesComponent plus(UserRepliesModule userRepliesModule);
  UserComponent plus(UserModule userModule, UserFollowingModule userFollowingModule);
  SignInComponent plus(SignInModule signInModule, UserModule userModule);
  SiteBaseComponent plus(SiteBaseModule siteBaseModule);
  NotificationsBaseComponent plus(NotificationsBaseModule notificationsBaseModule);
  NewsBaseComponent plus(NewsBaseModule newsBaseModule);
  CreateNewsBaseComponent plus(CreateNewsBaseModule createNewsBaseModule, NewsNodesBaseModule newsNodesBaseModule);

}
