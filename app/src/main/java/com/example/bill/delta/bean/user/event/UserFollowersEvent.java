package com.example.bill.delta.bean.user.event;

import com.example.bill.delta.bean.user.UserInfo;
import java.util.List;

public class UserFollowersEvent {
    private List<UserInfo> userInfoList;

    public UserFollowersEvent(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }
}
