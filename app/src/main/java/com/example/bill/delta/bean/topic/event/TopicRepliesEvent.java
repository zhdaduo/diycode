package com.example.bill.delta.bean.topic.event;


import com.example.bill.delta.bean.topic.TopicReply;
import java.util.List;

public class TopicRepliesEvent {
    private List<TopicReply> topicReplyList;

    public TopicRepliesEvent(List<TopicReply> topicReplyList) {
        this.topicReplyList = topicReplyList;
    }

    public List<TopicReply> getTopicReplyList() {
        return topicReplyList;
    }
}
