package com.example.bill.delta.bean.news.event;


import com.example.bill.delta.bean.news.News;

public class CreateNewsEvent {
    private News news;

    public CreateNewsEvent(News news) {
        this.news = news;
    }

    public News getNews() {
        return news;
    }
}
