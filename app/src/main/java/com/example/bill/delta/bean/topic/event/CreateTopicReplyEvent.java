package com.example.bill.delta.bean.topic.event;

public class CreateTopicReplyEvent {
    private boolean isSuccessful;

    public CreateTopicReplyEvent(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
