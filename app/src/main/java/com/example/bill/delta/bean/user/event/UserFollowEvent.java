package com.example.bill.delta.bean.user.event;

public class UserFollowEvent {
    private boolean result;

    public UserFollowEvent(boolean result) {
        this.result = result;
    }

    public boolean getUserFollow() {
        return result;
    }
}
