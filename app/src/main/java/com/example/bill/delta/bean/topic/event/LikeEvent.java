package com.example.bill.delta.bean.topic.event;

import com.example.bill.delta.bean.topic.Like;

public class LikeEvent {
    private Like like;

    public LikeEvent(Like like) {
        this.like = like;
    }

    public void setLike(Like like) {
        this.like = like;
    }
}
