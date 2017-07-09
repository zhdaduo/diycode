package com.example.bill.delta.bean.topic.event;

import com.example.bill.delta.bean.topic.Like;

public class UnLikeEvent {
    private Like like;

    public UnLikeEvent(Like like) {
        this.like = like;
    }

    public Like getLike() {
        return like;
    }
}
