package com.example.bill.delta.bean.user.event;

import com.example.bill.delta.bean.topic.Topic;
import java.util.List;

public class UserFavoriteTopicsEvent {
    private List<Topic> topicList;

    public UserFavoriteTopicsEvent(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}
