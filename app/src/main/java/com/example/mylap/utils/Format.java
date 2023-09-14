package com.example.mylap.utils;

import android.os.Build;
import android.text.Html;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public static int[] convertTimeMiliseconds(long miliSeconds) {
        int[] result = new int[3];
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliSeconds);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0, nên cộng thêm 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        result[0] = day;
        result[1] = month;
        result[2] = year;

        return result;
    }
}
