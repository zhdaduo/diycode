package com.example.bill.delta.bean.user.event;


import com.example.bill.delta.bean.user.UserBlock;

public class UserBlockEvent {
    private UserBlock userBlock;

    public UserBlockEvent(UserBlock userBlock) {
        this.userBlock = userBlock;
    }

    public UserBlock getUserBlock() {
        return userBlock;
    }
}
