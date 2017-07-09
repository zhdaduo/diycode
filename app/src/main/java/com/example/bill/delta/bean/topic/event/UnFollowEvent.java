package com.example.bill.delta.bean.topic.event;

public class UnFollowEvent {
    private boolean result;

    public UnFollowEvent(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
