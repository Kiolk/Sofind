package com.github.kiolk.sofind.providers;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LanguageProvider {

    public static final String LANGUAGE_PREFIX = "LanguagePrefix";
    public static final String LANGUAGE_RU = "ru";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_BE = "be";


    public static String getLanguage(Context context){
        return PrefGetter.getString(context, LANGUAGE_PREFIX);
    }

    public static void setLanguage(Context context, String prefix){
        PrefSetter.putString(context,LANGUAGE_PREFIX,  prefix);

    }

    public static void changeLocale(Context context, String prefix){
        Locale locale = new Locale(prefix);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        }
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
