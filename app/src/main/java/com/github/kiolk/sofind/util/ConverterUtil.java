package com.github.kiolk.sofind.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConverterUtil {

    public static final String DAY_PATTERN = "dd MMM yyyy";

    public static String convertEpochTime(Context context, Long date, String pattern){
        Date day = new Date(date);
        Locale locale = context.getResources().getConfiguration().locale;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
        return formatter.format(day);
    }
}


//    val DATE_PATTERN: String = "dd MMM yyyy - hh:mm:ss"
//        val DURATION_TIME : String = "KK:mm:ss"
//        val SHORT_DURATION_TIME : String = "KK:mm"
//        val DAY_PATERN : String = "dd.MM.yyyy"
//        val SLASH_DAY_PATERN : String =
//        val TIMER_PATTERN : String = "hh:mm:ss"
//        }
//
//        fun convertEpochTime(date : Long, context : Context?, pattern : String, isAbsoluteTime: Boolean = false) : String {
//        val day =  Date(date)
//        val locale : Locale? = context?.resources?.configuration?.locale
//        val formatter = SimpleDateFormat(pattern, locale)
//        if(isAbsoluteTime) {
//        formatter.timeZone = TimeZone.getTimeZone("Africa/Banjul")
//        }
//        return formatter.format(day)
