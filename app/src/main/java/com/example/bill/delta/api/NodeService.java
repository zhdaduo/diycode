package com.example.bill.delta.api;

import com.example.bill.delta.bean.topicnode.Node;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NodeService {

    /**
     * 获取节点列表
     */
    @GET("nodes.json")
    Call<List<Node>> readNodes();
}
