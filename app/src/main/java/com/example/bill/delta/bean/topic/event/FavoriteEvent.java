package com.example.bill.delta.bean.topic.event;

public class FavoriteEvent {
    private boolean result;

    public FavoriteEvent(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
