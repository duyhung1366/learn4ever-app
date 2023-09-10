package com.example.mylap.utils;

import android.os.Build;
import android.text.Html;

public class Format {
    public static String formatText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CharSequence formattedText = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
            return formattedText.toString();
        } else {
            CharSequence formattedText = Html.fromHtml(text);
            return formattedText.toString();
        }
    }
}
