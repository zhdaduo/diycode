package com.example.bill.delta.bean.user;

import com.google.gson.annotations.SerializedName;

public class UserUnBlock {
    @SerializedName("ok") private int ok;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }
}
