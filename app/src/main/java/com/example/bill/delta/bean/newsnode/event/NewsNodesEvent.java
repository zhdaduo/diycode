package com.example.bill.delta.bean.newsnode.event;

import com.example.bill.delta.bean.newsnode.NewsNode;
import java.util.List;

public class NewsNodesEvent {
    private List<NewsNode> newsNodeList;

    public NewsNodesEvent(List<NewsNode> newsNodeList) {
        this.newsNodeList = newsNodeList;
    }

    public List<NewsNode> getNewsNodeList() {
        return newsNodeList;
    }
}
