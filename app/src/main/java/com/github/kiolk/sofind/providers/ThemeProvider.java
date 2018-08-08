package com.github.kiolk.sofind.providers;

import android.app.Activity;
import android.content.Context;

import com.github.kiolk.sofind.R;

/**
 * Class management manipulation with Theme and AccentColor
 */
public final class ThemeProvider {

    private static final String THEME = "Theme";
    private static final String ACCENT_COLOR = "Accent";
    public static final String DAY_MODE = "day";
    public static final String NIGHT_MODE = "night";
    public static final String RED_ACCENT = "red";
    public static final String GREEN_ACCENT = "green";
    public static final String BLUE_ACCENT = "blue";

    public static void applyTheme(final Activity activity) {
        final String theme = PrefGetter.getString(activity, THEME);
        final String accentColor = PrefGetter.getString(activity, ACCENT_COLOR);
        activity.setTheme(getTheme(theme, accentColor));
    }

    //TODO convert to Map
    private static int getTheme(final String theme, final String accaentColor) {
        final int selectedTheme;
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

    public static String getThemMode(final Context context) {
        return PrefGetter.getString(context, THEME);
    }

    public static void setThemeMode(final Context context, final String modType) {
        PrefSetter.putString(context, THEME, modType);
    }

    public static String getAccentColor(final Context context) {
        return PrefGetter.getString(context, ACCENT_COLOR);
    }

    public static void setAccentColor(final Context context, final String color) {
        PrefSetter.putString(context, ACCENT_COLOR, color);
    }
}



















