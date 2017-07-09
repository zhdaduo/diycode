package com.example.bill.delta.bean.user.event;

import com.example.bill.delta.bean.user.UserInfo;
import java.util.List;

public class UserFollowingEvent {
    private List<UserInfo> userInfoList;

    public UserFollowingEvent(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }
}
