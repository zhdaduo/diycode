package com.example.bill.delta.view.adapter.topic;


import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.bean.topic.TopicReply;

public class TopicReplyWithTopic {
    private TopicDetail topicDetail;
    private TopicReply topicReply;

    public TopicReplyWithTopic(TopicDetail topicDetail, TopicReply topicReply) {
        this.topicDetail = topicDetail;
        this.topicReply = topicReply;
    }

    public TopicDetail getTopicDetail() {
        return topicDetail;
    }

    public TopicReply getTopicReply() {
        return topicReply;
    }
}
