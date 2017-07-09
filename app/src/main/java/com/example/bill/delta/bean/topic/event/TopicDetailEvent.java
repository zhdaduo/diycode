package com.example.bill.delta.bean.topic.event;


import com.example.bill.delta.bean.topic.TopicDetail;

public class TopicDetailEvent {
    private TopicDetail topicDetail;

    public TopicDetailEvent(TopicDetail topicDetail) {
        this.topicDetail = topicDetail;
    }

    public TopicDetail getTopicDetail() {
        return topicDetail;
    }
}
