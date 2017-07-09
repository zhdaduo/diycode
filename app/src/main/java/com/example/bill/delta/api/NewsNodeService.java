package com.example.bill.delta.api;

import com.example.bill.delta.bean.newsnode.NewsNode;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsNodeService {

  /**
   * 获取节点列表
   */
  @GET("news/nodes.json")
  Call<List<NewsNode>> readNewsNodes();
}
