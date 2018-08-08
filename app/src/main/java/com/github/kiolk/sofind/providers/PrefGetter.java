package com.github.kiolk.sofind.providers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class for store some primitives to SharedPreferences
 */
public final class PrefGetter {

    static final String APP_PREFERENCES = "Preferences";
    public static final String DEFAULT_VALUE = "defaultValue";

    static String getString(final Context context, final String key) {
        final SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(key, DEFAULT_VALUE);
    }

}
