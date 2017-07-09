package com.example.bill.delta.bean.topic.event;

public class UnFavoriteEvent {
    private boolean result;

    public UnFavoriteEvent(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
