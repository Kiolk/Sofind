package com.github.kiolk.sofind.providers;

import android.app.Activity;

import com.github.kiolk.sofind.R;

public class ThemeProvider {

    private static final String THEME = "Theme";
    private static final String ACCENT_COLOR = "Theme";


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
                        break;

                }
                break;
            case "night":
                switch (accaentColor) {
                    case "red":
                        break;
                    case "blue":
                        break;
                    default:
                        break;
                }
                break;
            default:
                selectedTheme = R.style.Light;
                break;
        }

        return selectedTheme;
    }
}
