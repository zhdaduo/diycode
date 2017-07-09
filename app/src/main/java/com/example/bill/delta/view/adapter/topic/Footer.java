package com.example.bill.delta.view.adapter.topic;

public class Footer {
    public static final int STATUS_NORMAL = 1;  //normal
    public static final int STATUS_LOADING = 2; //loading
    public static final int STATUS_NO_MORE = 3; //no more
    private int status;

    public Footer(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}