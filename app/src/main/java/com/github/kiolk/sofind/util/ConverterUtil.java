package com.github.kiolk.sofind.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class ConverterUtil {

    public static final String DAY_PATTERN = "dd MMM yyyy hh:mm";

    /**
     * Method for convert EpochTime to typical representation date and time for current location
     *
     * @param context - Context
     * @param date    - EpochTime presented as Long
     * @param pattern - String object with pattern of date and time display
     * @return String object with representation of EpochTime
     */
    public static String convertEpochTime(final Context context, final Long date, final String pattern) {
        final Date day = new Date(date);
        final Locale locale = context.getResources().getConfiguration().locale;
        final SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
        return formatter.format(day);
    }
}