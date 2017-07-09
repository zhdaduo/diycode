package com.example.bill.delta.api;

import com.example.bill.delta.bean.site.Site;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SiteService {

    /**
     * 获取酷站信息
     */
    @GET("sites.json")
    Call<List<Site>> getSite();
}
