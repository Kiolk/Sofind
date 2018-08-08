package com.github.kiolk.sofind.providers;

import android.app.Activity;
import android.content.Context;

import com.github.kiolk.sofind.R;

public class ThemeProvider {

    private static final String THEME = "Theme";
    private static final String ACCENT_COLOR = "Accent";
    public static final String DAY_MODE = "day";
    public static final String NIGHT_MODE = "night";
    public static final String RED_ACCENT= "red";
    public static final String GREEN_ACCENT = "green";
    public static final String BLUE_ACCENT= "blue";



    public static void applyTheme(Activity activity) {
        String theme = PrefGetter.getString(activity, THEME);
        String accentColor = PrefGetter.getString(activity, ACCENT_COLOR);
        activity.setTheme(getTheme(theme, accentColor));
    }

    //TODO convert to Map
    private static int getTheme(String theme, String accaentColor) {
        int selectedTheme = 0;
        switch (theme) {
            case DAY_MODE:
                switch (accaentColor) {
                    case RED_ACCENT:
                        selectedTheme = R.style.RedLight;
                        break;
                    case BLUE_ACCENT:
                        selectedTheme = R.style.BlueLight;
                        break;
                        case GREEN_ACCENT:
                            selectedTheme = R.style.GreenLight;
                        break;
                    default:
                        selectedTheme = R.style.Light;
                        break;
                }
                break;
            case NIGHT_MODE:
                switch (accaentColor) {
                    case RED_ACCENT:
                        selectedTheme = R.style.RedDark;
                        break;
                    case BLUE_ACCENT:
                        selectedTheme = R.style.BlueDark;
                        break;
                        case GREEN_ACCENT:
                            selectedTheme = R.style.GreenDark;
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

    public static String getAccentColor(Context context) {
        return PrefGetter.getString(context, ACCENT_COLOR);
    }

    public static void setAccentColor(Context context, String color){
        PrefSetter.putString(context, ACCENT_COLOR, color);
    }
}



















