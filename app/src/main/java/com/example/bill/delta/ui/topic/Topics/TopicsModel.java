package com.example.bill.delta.ui.topic.Topics;

import android.util.Log;
import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.bean.topic.Topic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.Subscriber;

public class TopicsModel implements TopicsMVP.Model {

  private static final String TAG = "TopicsModel";

  @Inject
  TopicService service;

  @Inject
  public TopicsModel() {
  }

  @Override
  public Observable<List<Topic>> getTopicsObservable(final Integer offset) {
    Observable observable = Observable.create(new Observable.OnSubscribe<List<Topic>>() {

      @Override
      public void call(final Subscriber<? super List<Topic>> subscriber) {
        Call<List<Topic>> call = service.getTopics(null, null, offset, null);
        call.enqueue(new Callback<List<Topic>>() {
          @Override
          public void onResponse(Call<List<Topic>> call,
              retrofit2.Response<List<Topic>> response) {
            if (response.isSuccessful()) {
              List<Topic> topicList = response.body();
              Log.v(TAG, "topicList:" + topicList);
              subscriber.onNext(topicList);
              subscriber.onCompleted();
            } else {
              Log.e(TAG, "getTopics STATUS: " + response.code());
            }
          }

          @Override
          public void onFailure(Call<List<Topic>> call, Throwable t) {
            Log.e(TAG, t.getMessage());
            subscriber.onError(t);
          }
        });
      }
    });
    return observable;
  }

  @Override
  public Observable<List<Topic>> getTopTopicsObservable() {
    return  Observable.create(new Observable.OnSubscribe<List<Topic>>() {
      @Override
      public void call(final Subscriber<? super List<Topic>> subscriber) {
        new Thread(new Runnable() {
          @Override public void run() {
            try {
              Document doc = Jsoup.connect("https://www.diycode.cc/").get();
              int size = doc.getElementsByClass("fa fa-thumb-tack").size();
              Log.d(TAG, "top size: " + size);
              Elements elements = doc.getElementsByClass("panel-body");
              Elements topics = elements.get(0).children();
              Log.d(TAG, "topics size: " + topics.size());
              List<Topic> topicList = new ArrayList<>();
              for (int i = 0; i < size; i++) {
                Element topic = topics.get(i);
                Topic temp = new Topic();
                String href = topic.getElementsByClass("title media-heading")
                    .get(0)
                    .getElementsByTag("a")
                    .attr("href");
                temp.setId(Integer.valueOf(href.substring(href.lastIndexOf("/") + 1)));
                temp.setTitle(
                    topic.getElementsByClass("title media-heading").get(0).text());
                temp.setNodeName(topic.getElementsByClass("node").get(0).text());
                String time = topic.getElementsByClass("timeago").get(0).attr("title");
                StringBuilder sb = new StringBuilder(time);
                sb.insert(19, ".000");
                time = sb.toString();
                temp.setRepliedAt(time);
                Topic.User user = new Topic.User();
                user.setAvatarUrl(topic.getElementsByTag("img").get(0).attr("src"));
                user.setLogin(topic.getElementsByClass("hacknews_clear").get(1).text());
                temp.setUser(user);
                temp.setPin(true);
                topicList.add(temp);
              }
              subscriber.onNext(topicList);
              subscriber.onCompleted();
            } catch (IOException e) {
              e.printStackTrace();
              subscriber.onError( e);
            }
          }
        }).start();
      }
    });
  }
}