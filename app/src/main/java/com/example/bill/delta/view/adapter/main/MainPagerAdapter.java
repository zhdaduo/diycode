package com.example.bill.delta.view.adapter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.example.bill.delta.ui.news.NewsBase.NewsFragment;
import com.example.bill.delta.ui.site.SitesFragment;
import com.example.bill.delta.ui.topic.Topics.TopicFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopicFragment.newInstance("", TopicFragment.TYPE_ALL);
            case 1:
                return new NewsFragment();
            case 2:
                return new SitesFragment();
            default:
                return null;
        }
    }

    @Override public int getCount() {
        return 3;
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "topics";
            case 1:
                return "news";
            case 2:
                return "sites";
            default:
                return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
