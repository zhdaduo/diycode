package com.example.bill.delta.api;


import com.example.bill.delta.bean.notification.Notification;
import com.example.bill.delta.bean.notification.NotificationDelete;
import com.example.bill.delta.bean.notification.NotificationsDelete;
import com.example.bill.delta.bean.notification.NotificationsRead;
import com.example.bill.delta.bean.notification.NotificationsUnreadCount;
import com.example.bill.delta.util.Constant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {

    /**
     * 获取当前用户的通知列表
     */
    @GET("notifications.json")
    Call<List<Notification>> readNotifications(
        @Header(Constant.KEY_TOKEN) String token, @Query("offset") Integer offset,
        @Query("limit") Integer limit);

    /**
     * 删除当前用户的某个通知
     */
    @DELETE("notifications/{id}.json") Call<NotificationDelete> deleteNotification(
        @Header(Constant.KEY_TOKEN) String token, @Path("id") int id);

    /**
     * 删除当前用户的所有通知
     */
    @DELETE("notifications/all.json") Call<NotificationsDelete> deleteAllNotifications(
        @Header(Constant.KEY_TOKEN) String token);

    /**
     * 将当前用户的一些通知设成已读状态
     */
    @POST("notifications/read.json") Call<NotificationsRead> markNotificationsAsRead(
        @Header(Constant.KEY_TOKEN) String token, @Query("ids") int[] ids);

    /**
     * 获得未读通知数量
     */
    @GET("notifications/unread_count.json") Call<NotificationsUnreadCount> unReadNotificationCount(
        @Header(Constant.KEY_TOKEN) String token);
}
