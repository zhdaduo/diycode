package com.example.bill.delta.bean.notification.event;


import com.example.bill.delta.bean.notification.NotificationsUnreadCount;

public class NotificationsUnreadCountEvent {
    private NotificationsUnreadCount notificationsUnreadCount;

    public NotificationsUnreadCountEvent(NotificationsUnreadCount notificationsUnreadCount) {
        this.notificationsUnreadCount = notificationsUnreadCount;
    }

    public NotificationsUnreadCount getNotificationsUnreadCount() {
        return notificationsUnreadCount;
    }
}
