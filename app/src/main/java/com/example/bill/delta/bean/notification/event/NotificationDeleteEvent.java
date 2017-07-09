package com.example.bill.delta.bean.notification.event;


import com.example.bill.delta.bean.notification.NotificationDelete;

public class NotificationDeleteEvent {
    private NotificationDelete notificationDelete;

    public NotificationDeleteEvent(NotificationDelete notificationDelete) {
        this.notificationDelete = notificationDelete;
    }

    public NotificationDelete getNotificationDelete() {
        return notificationDelete;
    }
}
