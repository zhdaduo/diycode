package com.example.bill.delta.util;

public class HtmlUtil {
    private static final String TAG = "HtmlUtil";

    public static String removeP(String html) {
        String result = html;
        LogUtil.d(TAG, "html: " + html);
        if (result.contains("<p>") && result.contains("</p>")) {
            result = result.replace("<p>", "");
            result = result.replace("</p>", "<br>");
            LogUtil.d(TAG, "result: " + result);
            if (result.endsWith("<br>")) {
                result = result.substring(0, result.length() - 4);
                LogUtil.d(TAG, "final: " + result);
            }
        }
        return result;
    }
}
