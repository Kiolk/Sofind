package com.github.kiolk.sofind.providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static com.github.kiolk.sofind.providers.PrefGetter.APP_PREFERENCES;

public class PrefSetter {

    //Refactor method to use generics
    public static void putString(Context context, String key, String value){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor =  preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
