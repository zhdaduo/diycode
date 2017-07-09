package com.example.bill.delta.ui.notification;

import android.util.Log;
import com.example.bill.delta.bean.notification.event.NotificationsEvent;
import com.example.bill.delta.ui.notification.NotificationsBaseMVP.Model;
import com.example.bill.delta.ui.notification.NotificationsBaseMVP.View;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NotificationsBasePresenter implements NotificationsBaseMVP.Presenter {

  private static final String TAG = "NotificationsPresenter";

  private NotificationsBaseMVP.Model mModel;
  private NotificationsBaseMVP.View mView;

  @Inject
  public NotificationsBasePresenter(
      Model mModel) {
    this.mModel = mModel;
  }

  public void readNotifications(int offset) {
    Log.d(TAG, "readNotifications");
    mModel.readNotifications(offset, null);
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void showNotifications(NotificationsEvent event) {
    Log.d(TAG, "showNotifications");
    mView.showNotifications(event.getNotificationList());
    EventBus.getDefault().removeStickyEvent(event);
  }

  public void bind(View view) {
    this.mView = view;
  }

  @Override
  public void unbind() {
    this.mView = null;
  }

  @Override
  public void start() {
    EventBus.getDefault().register(this);
  }

  @Override
  public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
