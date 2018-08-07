package com.github.kiolk.sofind.providers;

import android.app.Activity;
import android.content.Context;

import com.github.kiolk.sofind.R;

public class ThemeProvider {

    private static final String THEME = "Theme";
    private static final String ACCENT_COLOR = "Theme";
    public static final String DAY_MODE = "day";
    public static final String NIGHT_MODE = "night";


    public static void applyTheme(Activity activity) {
        String theme = PrefGetter.getString(activity, THEME);
        String accentColor = PrefGetter.getString(activity, ACCENT_COLOR);
        activity.setTheme(getTheme(theme, accentColor));
    }

    //TODO convert to Map
    private static int getTheme(String theme, String accaentColor) {
        int selectedTheme = 0;
        switch (theme) {
            case "day":
                switch (accaentColor) {
                    case "red":
                        break;
                    case "blue":
                        break;
                    default:
                        selectedTheme = R.style.Light;
                        break;
                }
//                selectedTheme = R.style.Light;
                break;
            case "night":
                switch (accaentColor) {
                    case "red":
                        break;
                    case "blue":
                        break;
                    default:
                        selectedTheme = R.style.Dark;
                        break;
                }
                break;
            default:
                selectedTheme = R.style.Dark;
                break;
        }

        return selectedTheme;
    }

    public static String getThemMode(Context context) {
        return PrefGetter.getString(context, THEME);
    }

    public static void setThemeMode(Context context, String modType) {
        PrefSetter.putString(context, THEME, modType);
    }
}



















