package com.example.bill.delta.bean.user.event;


import com.example.bill.delta.bean.user.UserDetailInfo;

public class MeEvent {
    private UserDetailInfo userDetailInfo;

    public MeEvent(UserDetailInfo userDetailInfo) {
        this.userDetailInfo = userDetailInfo;
    }

    public UserDetailInfo getUserDetailInfo() {
        return userDetailInfo;
    }
}
