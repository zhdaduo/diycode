package com.example.bill.delta.bean.topic.event;

public class FollowEvent {
    private boolean result;

    public FollowEvent(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
