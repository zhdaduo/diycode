package com.example.bill.delta.util;

import android.util.Patterns;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {
    /**
     * get URL's Host
     */
    public static String getHost(String urlString) {
        String result = urlString;
        try {
            URL url = new URL(urlString);
            result = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * get url from text
     */
    public static String getUrl(String text) {
        Pattern p = Patterns.WEB_URL;
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }
}
