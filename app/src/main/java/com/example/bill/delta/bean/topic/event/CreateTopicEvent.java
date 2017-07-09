package com.example.bill.delta.bean.topic.event;


import com.example.bill.delta.bean.topic.TopicDetail;

public class CreateTopicEvent {
    private TopicDetail topicDetail;

    public CreateTopicEvent(TopicDetail topicDetail) {
        this.topicDetail = topicDetail;
    }

    public TopicDetail getTopicDetail() {
        return topicDetail;
    }
}
