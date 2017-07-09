package com.example.bill.delta.view.adapter.about;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

public class Contributor {

    @DrawableRes public final int avatarResId;
    @NonNull public final String name;
    @NonNull public final String desc;
    public final String url;

    public Contributor(@DrawableRes int avatarResId, @NonNull String name, @NonNull String desc,
        String url) {
        this.avatarResId = avatarResId;
        this.name = name;
        this.desc = desc;
        this.url = url;
    }
}