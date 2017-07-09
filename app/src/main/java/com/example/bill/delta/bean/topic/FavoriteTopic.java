package com.example.bill.delta.bean.topic;

import com.google.gson.annotations.SerializedName;

public class FavoriteTopic {
    @SerializedName("ok") private int ok;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    @Override public String toString() {
        return "FavoriteTopic{" + "ok=" + ok + '}';
    }
}
