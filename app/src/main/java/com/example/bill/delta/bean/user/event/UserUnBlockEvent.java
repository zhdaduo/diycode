package com.example.bill.delta.bean.user.event;


import com.example.bill.delta.bean.user.UserUnBlock;

public class UserUnBlockEvent {
    private UserUnBlock userUnBlock;

    public UserUnBlockEvent(UserUnBlock userUnBlock) {
        this.userUnBlock = userUnBlock;
    }

    public UserUnBlock getUserUnBlock() {
        return userUnBlock;
    }
}
