package com.dicoding.newsapp.utils;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatter {
    @Nullable
    public static String formatDate(String currentDate) {
        String currentFormat = "yyyy-MM-dd'T'hh:mm:ss'Z'";
        String targetFormat = "dd MMM yyyy | HH:mm";
        String timezone = "GMT";
        DateFormat currentDf = new SimpleDateFormat(currentFormat, Locale.getDefault());
        currentDf.setTimeZone(TimeZone.getTimeZone(timezone));
        DateFormat targetDf = new SimpleDateFormat(targetFormat, Locale.getDefault());
        String targetDate = null;
        try {
            Date date = currentDf.parse(currentDate);
            if (date != null) {
                targetDate = targetDf.format(date);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return targetDate;
    }
}
