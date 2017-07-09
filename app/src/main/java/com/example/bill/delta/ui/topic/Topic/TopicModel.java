package com.example.bill.delta.ui.topic.Topic;

import android.util.Log;
import com.example.bill.delta.api.TopicService;
import com.example.bill.delta.bean.topic.FavoriteTopic;
import com.example.bill.delta.bean.topic.FollowTopic;
import com.example.bill.delta.bean.topic.Like;
import com.example.bill.delta.bean.topic.TopicDetail;
import com.example.bill.delta.bean.topic.UnFavoriteTopic;
import com.example.bill.delta.bean.topic.UnFollowTopic;
import com.example.bill.delta.bean.topic.event.FavoriteEvent;
import com.example.bill.delta.bean.topic.event.FollowEvent;
import com.example.bill.delta.bean.topic.event.LikeEvent;
import com.example.bill.delta.bean.topic.event.TopicDetailEvent;
import com.example.bill.delta.bean.topic.event.UnFavoriteEvent;
import com.example.bill.delta.bean.topic.event.UnFollowEvent;
import com.example.bill.delta.bean.topic.event.UnLikeEvent;
import dagger.Module;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicModel implements TopicMVP.Model {

  private static final String TAG = "TopicModel";

  @Inject
  TopicService service;

  @Inject
  public TopicModel() {

  }

  @Override
  public void getTopic(int id) {
    Call<TopicDetail> call = service.getTopic(id);
    call.enqueue(new Callback<TopicDetail>() {
      @Override
      public void onResponse(Call<TopicDetail> call,
          retrofit2.Response<TopicDetail> response) {
        if (response.isSuccessful()) {
          TopicDetail topicDetail = response.body();
          Log.v(TAG, "getTopic topicDetail:" + topicDetail);
          EventBus.getDefault().post(new TopicDetailEvent(topicDetail));
        } else {
          Log.e(TAG, "getTopic STATUS: " + response.code());
          EventBus.getDefault().post(new TopicDetailEvent(null));
        }
      }

      @Override
      public void onFailure(Call<TopicDetail> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new TopicDetailEvent(null));
      }
    });
  }

  @Override
  public void favorite(int id) {
    Call<FavoriteTopic> call = service.favoriteTopic(id);
    call.enqueue(new Callback<FavoriteTopic>() {
      @Override
      public void onResponse(Call<FavoriteTopic> call, Response<FavoriteTopic> response) {
        if (response.isSuccessful()) {
          FavoriteTopic favoriteTopic = response.body();
          Log.v(TAG, "favorite: " + favoriteTopic);
          EventBus.getDefault().post(new FavoriteEvent(favoriteTopic.getOk() == 1));
        } else {
          Log.e(TAG, "favorite STATUS: " + response.code());
          EventBus.getDefault().post(new FavoriteEvent(false));
        }
      }

      @Override
      public void onFailure(Call<FavoriteTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new FavoriteEvent(false));
      }
    });
  }

  @Override
  public void unFavorite(int id) {
    Call<UnFavoriteTopic> call = service.unFavoriteTopic(id);
    call.enqueue(new Callback<UnFavoriteTopic>() {
      @Override
      public void onResponse(Call<UnFavoriteTopic> call, Response<UnFavoriteTopic> response) {
        if (response.isSuccessful()) {
          UnFavoriteTopic unFavoriteTopic = response.body();
          Log.v(TAG, "unFavorite: " + unFavoriteTopic);
          EventBus.getDefault().post(new UnFavoriteEvent(unFavoriteTopic.getOk() == 1));
        } else {
          Log.e(TAG, "unFavorite STATUS: " + response.code());
          EventBus.getDefault().post(new UnFavoriteEvent(false));
        }
      }

      @Override
      public void onFailure(Call<UnFavoriteTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UnFavoriteEvent(false));
      }
    });
  }

  @Override
  public void follow(int id) {
    Call<FollowTopic> call = service.followTopic(id);
    call.enqueue(new Callback<FollowTopic>() {
      @Override
      public void onResponse(Call<FollowTopic> call, Response<FollowTopic> response) {
        if (response.isSuccessful()) {
          FollowTopic followTopic = response.body();
          Log.v(TAG, "follow: " + followTopic);
          EventBus.getDefault().post(new FollowEvent(followTopic.getOk() == 1));
        } else {
          Log.e(TAG, "follow STATUS: " + response.code());
          EventBus.getDefault().post(new FollowEvent(false));
        }
      }

      @Override
      public void onFailure(Call<FollowTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new FollowEvent(false));
      }
    });
  }

  @Override
  public void unFollow(int id) {
    Call<UnFollowTopic> call = service.unFollowTopic(id);
    call.enqueue(new Callback<UnFollowTopic>() {
      @Override
      public void onResponse(Call<UnFollowTopic> call, Response<UnFollowTopic> response) {
        if (response.isSuccessful()) {
          UnFollowTopic unFollowTopic = response.body();
          Log.v(TAG, "unFollow: " + unFollowTopic);
          EventBus.getDefault().post(new UnFollowEvent(unFollowTopic.getOk() == 1));
        } else {
          Log.e(TAG, "unFollow STATUS: " + response.code());
          EventBus.getDefault().post(new UnFollowEvent(false));
        }
      }

      @Override
      public void onFailure(Call<UnFollowTopic> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UnFollowEvent(false));
      }
    });
  }

  @Override
  public void like(String obj_type, Integer obj_id) {
    Call<Like> call = service.like(obj_type, obj_id);
    call.enqueue(new Callback<Like>() {
      @Override
      public void onResponse(Call<Like> call, Response<Like> response) {
        if (response.isSuccessful()) {
          Like like = response.body();
          Log.v(TAG, "like: " + like);
          EventBus.getDefault().post(new LikeEvent(like));
        } else {
          Log.e(TAG, "like STATUS: " + response.code());
          EventBus.getDefault().post(new LikeEvent(null));
        }
      }

      @Override
      public void onFailure(Call<Like> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new LikeEvent(null));
      }
    });
  }

  @Override
  public void unLike(String obj_type, Integer obj_id) {
    Call<Like> call = service.unLike(obj_type, obj_id);
    call.enqueue(new Callback<Like>() {
      @Override
      public void onResponse(Call<Like> call, Response<Like> response) {
        if (response.isSuccessful()) {
          Like like = response.body();
          Log.v(TAG, "unLike: " + like);
          EventBus.getDefault().post(new UnLikeEvent(like));
        } else {
          Log.e(TAG, "unLike STATUS: " + response.code());
          EventBus.getDefault().post(new UnLikeEvent(null));
        }
      }

      @Override
      public void onFailure(Call<Like> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new UnLikeEvent(null));
      }
    });
  }
}
