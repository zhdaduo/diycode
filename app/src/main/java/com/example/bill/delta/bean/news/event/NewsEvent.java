package com.example.bill.delta.bean.news.event;


import com.example.bill.delta.bean.news.News;
import java.util.List;

public class NewsEvent {
    private List<News> newsList;

    public NewsEvent(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }
}
