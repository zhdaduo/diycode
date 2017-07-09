package com.example.bill.delta.bean.topic.event;


import com.example.bill.delta.bean.topic.Reply;
import java.util.List;

public class RepliesEvent {
    private List<Reply> replyList;

    public RepliesEvent(List<Reply> replyList) {
        this.replyList = replyList;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }
}
