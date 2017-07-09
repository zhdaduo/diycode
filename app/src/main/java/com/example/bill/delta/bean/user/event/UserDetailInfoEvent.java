package com.example.bill.delta.bean.user.event;


import com.example.bill.delta.bean.user.UserDetailInfo;

public class UserDetailInfoEvent {
    private UserDetailInfo userDetailInfo;

    public UserDetailInfoEvent(UserDetailInfo userDetailInfo) {
        this.userDetailInfo = userDetailInfo;
    }

    public UserDetailInfo getUserDetailInfo() {
        return userDetailInfo;
    }
}
