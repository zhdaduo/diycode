package com.example.bill.delta.bean.user.event;

public class UserUnFollowEvent {
    private boolean userUnFollow;

    public UserUnFollowEvent(boolean userUnFollow) {
        this.userUnFollow = userUnFollow;
    }

    public boolean getUserUnFollow() {
        return userUnFollow;
    }
}
