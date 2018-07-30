package com.github.kiolk.sofind.providers;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefGetter {

    public static final String APP_PREFERENCES = "Preferences";
    public  static  final  String DEFAULT_VALUE = "defaultValue";

    public static String getString(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(key, DEFAULT_VALUE);
    }

}
