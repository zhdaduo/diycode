package com.example.bill.delta.bean.topic.event;


import com.example.bill.delta.bean.topic.Topic;
import java.util.List;

public class TopTopicsEvent {
    private List<Topic> topicList;

    public TopTopicsEvent(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}
