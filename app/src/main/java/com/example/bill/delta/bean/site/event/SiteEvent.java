package com.example.bill.delta.bean.site.event;

import com.example.bill.delta.bean.site.Site;
import java.util.List;

public class SiteEvent {
    private List<Site> siteList;

    public SiteEvent(List<Site> siteList) {
        this.siteList = siteList;
    }

    public List<Site> getSiteList() {
        return siteList;
    }
}
