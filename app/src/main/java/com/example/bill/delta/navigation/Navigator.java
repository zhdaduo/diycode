package com.example.bill.delta.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.example.bill.delta.ui.main.MainActivity;
import com.example.bill.delta.ui.news.CreateNews.CreateNewsActivity;
import com.example.bill.delta.ui.notification.NotificationActivity;
import com.example.bill.delta.ui.topic.CreateTopic.CreateTopicActivity;
import com.example.bill.delta.ui.topic.CreateTopicReply.CreateTopicReplyActivity;
import com.example.bill.delta.ui.topic.Topic.TopicActivity;
import com.example.bill.delta.ui.user.User.UserActivity;
import com.example.bill.delta.ui.user.UserReplies.MyUserRepliesActivity;
import com.example.bill.delta.view.activity.AboutActivity;
import com.example.bill.delta.view.activity.MyTopicsActivity;
import com.example.bill.delta.view.activity.SearchActivity;
import com.example.bill.delta.view.activity.SettingsActivity;
import com.example.bill.delta.view.activity.WebActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }

  /**
   * Goes to the create topic screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToCreateTopicActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = CreateTopicActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the create news screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToCreateNewsActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = CreateNewsActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the user screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserActivity(Context context, String loginName) {
    if (context != null) {
      Intent intentToLaunch = UserActivity.getCallingIntent(context, loginName);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the user screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToUserActivity(Context context, String loginName, boolean isFollowed) {
    if (context != null) {
      Intent intentToLaunch = UserActivity.getCallingIntent(context, loginName, isFollowed);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the setting screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToSettingsActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = SettingsActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the notification screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToNotificationActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = NotificationActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the my topics screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMyTopicsActivity(Context context, String title, int type) {
    if (context != null) {
      Intent intentToLaunch = MyTopicsActivity.getCallingIntent(context, title, type);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the about screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToAboutActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = AboutActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the user replies screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMyUserRepliesActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = MyUserRepliesActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the web screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToWebActivity(Context context, String url) {
    if (context != null) {
      Intent intentToLaunch = WebActivity.getCallingIntent(context, url);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the topic screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToTopicActivity(Context context, int id) {
    if (context != null) {
      Intent intentToLaunch = TopicActivity.getCallingIntent(context, id);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the create topic reply screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToCreateTopicReplyActivity(Context context, int id, String title) {
    if (context != null) {
      Intent intentToLaunch = CreateTopicReplyActivity.getCallingIntent(context, id, title);
      context.startActivity(intentToLaunch);
    }
  }

  public void navigateToCreateTopicReplyActivityMore(Context context, int id, String title,
      String to) {
    if (context != null) {
      Intent intentToLaunch = CreateTopicReplyActivity.getCallingIntentMore(context, id, title, to);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the main screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMainActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = MainActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the search screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToSearchActivity(Context context) {
    if (context != null) {
      Intent intentToLaunch = SearchActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * open browser and goto sign up page
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToSignUp(Context context) {
    if (context != null) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse("https://www.diycode.cc/account/sign_up"));
      context.startActivity(intent);
    }
  }

  public void navigateToForget(Context context) {
    if (context != null) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse("https://www.diycode.cc/account/password/new"));
      context.startActivity(intent);
    }
  }
}
