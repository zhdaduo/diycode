package com.example.bill.delta.ui.site;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bill.delta.AndroidApplication;
import com.example.bill.delta.R;
import com.example.bill.delta.bean.site.Site;
import com.example.bill.delta.navigation.Navigator;
import com.example.bill.delta.ui.base.BaseFragment;
import com.example.bill.delta.util.LogUtil;
import com.example.bill.delta.view.Listener.ISiteListener;
import com.example.bill.delta.view.adapter.site.SiteName;
import com.example.bill.delta.view.adapter.site.SiteNameViewProvider;
import com.example.bill.delta.view.adapter.site.SitesName;
import com.example.bill.delta.view.adapter.site.SitesNameViewProvider;
import com.example.bill.delta.view.widget.EmptyRecyclerView;
import java.util.List;
import javax.inject.Inject;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class SitesFragment extends BaseFragment implements SiteBaseMVP.View {

  private static final String TAG = "SitesFragment";
  private final static int SPAN_COUNT = 2;
  private Items items = new Items();
  private MultiTypeAdapter adapter;

  @BindView(R.id.rv_site_category) EmptyRecyclerView rvSiteCategory;
  @BindView(R.id.empty_view) ProgressBar emptyView;

  @Inject SiteBasePresenter siteBasePresenter;
  @Inject Navigator navigator;

  public SitesFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ((AndroidApplication) getActivity().getApplication()).getApplicationComponent()
        .plus(new SiteBaseModule(this))
        .inject(this);

    adapter = new MultiTypeAdapter(items);
    adapter.register(SiteName.class, new SiteNameViewProvider());
    adapter.register(SitesName.class, new SitesNameViewProvider(listener));
  }

  @Override
  public void fetchData() {
    siteBasePresenter.getSite();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_site, container, false);
    ButterKnife.bind(this, rootView);

    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return (items.get(position) instanceof SiteName) ? SPAN_COUNT : 1;
      }
    });
    rvSiteCategory.setLayoutManager(layoutManager);
    rvSiteCategory.setAdapter(adapter);
    rvSiteCategory.setEmptyView(emptyView);
    return rootView;
  }

  @Override
  public void showSite(List<Site> siteList) {
    LogUtil.v(TAG, "showSite: " + siteList);
    if (siteList == null) {
      return;
    }

    for (Site site : siteList) {
      items.add(new SiteName(site.getName(), site.getId()));
      for (Site.Sites sites : site.getSites()) {
        items.add(new SitesName(sites.getName(), sites.getUrl(), sites.getAvatarUrl()));
      }
      /*fixed bug : full grid*/
      if (site.getSites().size() % 2 == 1) {
        items.add(new SitesName(null, "", ""));
      }
    }

    adapter.notifyDataSetChanged();
  }

  @Override public void onStart() {
    super.onStart();
    if (siteBasePresenter != null) {
      siteBasePresenter.bind(this);
      siteBasePresenter.start();
    }
  }

  @Override public void onStop() {
    if (siteBasePresenter != null) {
      siteBasePresenter.unbind();
      siteBasePresenter.stop();
    }
    super.onStop();
  }

  ISiteListener listener = new ISiteListener() {
    @Override
    public void SiteListener(View view, String url) {
      navigator.navigateToWebActivity(view.getContext(), url);
    }
  };
}
