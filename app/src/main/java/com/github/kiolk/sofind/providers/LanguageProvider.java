package com.github.kiolk.sofind.providers;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

/**
 * Class has some static method for manipulate with languages. Store, reade and change language
 */
public final class LanguageProvider {

    private static final String LANGUAGE_PREFIX = "LanguagePrefix";
    public static final String LANGUAGE_RU = "ru";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_BE = "be";

    public static String getLanguage(final Context context) {
        return PrefGetter.getString(context, LANGUAGE_PREFIX);
    }

    public static void setLanguage(final Context context, final String prefix) {
        PrefSetter.putString(context, LANGUAGE_PREFIX, prefix);

    }

    public static void changeLocale(final Context context, final String prefix) {
        final Locale locale = new Locale(prefix);
        Locale.setDefault(locale);
        final Configuration configuration = context.getResources().getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        }

        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
