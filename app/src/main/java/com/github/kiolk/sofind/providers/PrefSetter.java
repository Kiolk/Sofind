package com.github.kiolk.sofind.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static com.github.kiolk.sofind.providers.PrefGetter.APP_PREFERENCES;

/**
 * Class for management of getting data from SharedPreferences
 */
final class PrefSetter {

    //Refactor method to use generics
    static void putString(final Context context, final String key, final String value) {
        final SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
