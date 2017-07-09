package com.example.bill.delta.util;

/**
 * click the image in html, delegate callback
 * use to custom click event
 */
public interface SpanClickListener {
    void onClick(int type, String url);
}