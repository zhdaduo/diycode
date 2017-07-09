package com.example.bill.delta.ui.notification;

import com.example.bill.delta.bean.notification.Notification;
import com.example.bill.delta.ui.base.BasePresenter;
import com.example.bill.delta.ui.base.BaseView;
import java.util.List;

public interface NotificationsBaseMVP {

  interface View extends BaseView {

    void showNotifications(List<Notification> notificationList);
  }

  interface Presenter extends BasePresenter<View> {

  }

  interface Model {

    void readNotifications(Integer offset, Integer limit);
  }
}
