package com.example.bill.delta.bean.notification.event;

import com.example.bill.delta.bean.notification.Notification;
import java.util.List;

public class NotificationsEvent {
    private List<Notification> notificationList;

    public NotificationsEvent(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }
}
