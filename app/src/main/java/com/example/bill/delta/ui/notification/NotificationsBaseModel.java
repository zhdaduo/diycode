package com.example.bill.delta.ui.notification;

import android.util.Log;
import com.example.bill.delta.api.NotificationService;
import com.example.bill.delta.bean.notification.Notification;
import com.example.bill.delta.bean.notification.event.NotificationsEvent;
import com.example.bill.delta.util.Constant;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsBaseModel implements NotificationsBaseMVP.Model {

  private static final String TAG = "NotificationsBaseModel";

  @Inject
  NotificationService notificationService;

  @Inject
  public NotificationsBaseModel() {
  }

  @Override
  public void readNotifications(Integer offset, Integer limit) {
    Call<List<Notification>> call =
        notificationService.readNotifications(Constant.VALUE_TOKEN_PREFIX + Constant.VALUE_TOKEN, offset,
            limit);
    call.enqueue(new Callback<List<Notification>>() {
      @Override public void onResponse(Call<List<Notification>> call,
          Response<List<Notification>> response) {
        if (response.isSuccessful()) {
          List<Notification> notificationList = response.body();
          Log.v(TAG, "notificationList: " + notificationList);
          EventBus.getDefault().postSticky(new NotificationsEvent(notificationList));
        } else {
          Log.e(TAG, "readNotifications STATUS: " + response.code());
          EventBus.getDefault().postSticky(new NotificationsEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Notification>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().postSticky(new NotificationsEvent(null));
      }
    });
  }
}
