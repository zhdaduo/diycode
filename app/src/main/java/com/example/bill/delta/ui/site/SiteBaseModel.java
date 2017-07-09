package com.example.bill.delta.ui.site;

import android.util.Log;
import com.example.bill.delta.api.SiteService;
import com.example.bill.delta.bean.site.Site;
import com.example.bill.delta.bean.site.event.SiteEvent;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiteBaseModel implements SiteBaseMVP.Model {

  private static final String TAG = "SiteBaseModel";

  @Inject
  SiteService service;

  @Inject
  public SiteBaseModel() {
  }

  @Override
  public void getSite() {
    Call<List<Site>> call = service.getSite();

    call.enqueue(new Callback<List<Site>>() {
      @Override public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
        if (response.isSuccessful()) {
          List<Site> siteList = response.body();
          Log.v(TAG, "siteList:" + siteList);
          EventBus.getDefault().post(new SiteEvent(siteList));
        } else {
          Log.e(TAG, "getSite STATUS: " + response.code());
          EventBus.getDefault().post(new SiteEvent(null));
        }
      }

      @Override public void onFailure(Call<List<Site>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
        EventBus.getDefault().post(new SiteEvent(null));
      }
    });
  }
}
