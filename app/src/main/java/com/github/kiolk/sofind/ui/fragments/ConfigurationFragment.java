package com.github.kiolk.sofind.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.adapters.SimpleArrayAdapter;
import com.github.kiolk.sofind.data.models.SettingItemModel;
import com.github.kiolk.sofind.providers.LanguageProvider;
import com.github.kiolk.sofind.providers.ThemeProvider;
import com.github.kiolk.sofind.ui.activities.home.HomeActivity;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;

import java.util.ArrayList;

/**
 * Class for showing fragment with general settings of application
 */
public class ConfigurationFragment extends BaseFragment {

    public static final float ENABLE_ELEVATION = 5.0f;
    public static final float DISABLE_ELEVATION = 1.0F;

    private void changeTitle() {
        super.titleResource = R.string.SETTING;
    }

    private void changeMenu() {
        super.menuId = 0;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        changeTitle();
        changeMenu();
        setupToolBar();
        return inflater.inflate(R.layout.fragment_with_list_view, null);
    }

    public void setSettingItems() {
        final ListAdapter adapter = new SimpleArrayAdapter(getContext(), R.layout.layout_setting_item, getSettingsItem(getContext()));
        final ListView list = getView().findViewById(R.id.setting_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                switch (position) {
                    case 0:
//                        Toast.makeText(getContext(), "Selected language", Toast.LENGTH_SHORT).show();
                        showSelectLanguageDialog();
                        break;
                    case 1:
//                        Toast.makeText(getContext(), "Selected Accent color", Toast.LENGTH_SHORT).show();
                        showSelectAccentColor();
                        break;
                    case 2:
//                        Toast.makeText(getContext(), "Selected mode", Toast.LENGTH_SHORT).show();
                        showSelectModDialog();
                        break;
                }
            }
        });
    }

    private void showSelectAccentColor() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        final LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_accent_color, null);
        final String[] accentColor = {ThemeProvider.getAccentColor(getContext())};
        final FloatingActionButton redButton = view.findViewById(R.id.red_fab);
        final FloatingActionButton blueButton = view.findViewById(R.id.blue_fab);
        final FloatingActionButton greenButton = view.findViewById(R.id.green_fab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            blueButton.setElevation(DISABLE_ELEVATION);
            redButton.setElevation(DISABLE_ELEVATION);
            greenButton.setElevation(DISABLE_ELEVATION);

            switch (accentColor[0]) {
                case ThemeProvider.RED_ACCENT:
                    redButton.setElevation(ENABLE_ELEVATION);
                    break;
                case ThemeProvider.BLUE_ACCENT:

                    blueButton.setElevation(ENABLE_ELEVATION);
                    break;
                case ThemeProvider.GREEN_ACCENT:
                    greenButton.setElevation(ENABLE_ELEVATION);
                    break;
            }
        }

        final View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                deselectElevationButton();
                switch (v.getId()) {
                    case R.id.red_fab:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            redButton.setElevation(5.0f);
                        }
                        accentColor[0] = ThemeProvider.RED_ACCENT;
                        break;
                    case R.id.blue_fab:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            blueButton.setElevation(5.0f);
                        }
                        accentColor[0] = ThemeProvider.BLUE_ACCENT;
                        break;
                    case R.id.green_fab:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            greenButton.setElevation(ENABLE_ELEVATION);
                        }
                        accentColor[0] = ThemeProvider.GREEN_ACCENT;
                        break;
                }
            }

            private void deselectElevationButton() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    blueButton.setElevation(DISABLE_ELEVATION);
                    redButton.setElevation(DISABLE_ELEVATION);
                    greenButton.setElevation(DISABLE_ELEVATION);
                }
            }
        };

        redButton.setOnClickListener(listener);
        greenButton.setOnClickListener(listener);
        blueButton.setOnClickListener(listener);
        dialog.setTitle(R.string.SELECT_ACCENT_COLOR)
                .setView(view)
                .setPositiveButton(R.string.APPLY_CHANGES, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        ThemeProvider.setAccentColor(getContext(), accentColor[0]);
                        final HomeActivity home = (HomeActivity) getActivity();
                        if (home != null) {
                            home.restart();
                        }
                    }
                })
                .create()
                .show();
    }

    private void showSelectLanguageDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        final String currentLanguage = LanguageProvider.getLanguage(getContext());
        final String[] availableLanguages = getResources().getStringArray(R.array.LANGUAGES);
        final String[] fullNameLanguages = getResources().getStringArray(R.array.LANGUAGES_FULL_NAME);
        final int[] position = {1};

        for (int index = 0; index < availableLanguages.length; ++index) {
            if (currentLanguage.equals(availableLanguages[index])) {
                position[0] = index;
            }
        }

        dialog.setTitle(R.string.CHOSE_LANGUAGE).setSingleChoiceItems(fullNameLanguages, position[0], new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                position[0] = which;
            }
        }).setPositiveButton(R.string.APPLY_CHANGES, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                LanguageProvider.setLanguage(getContext(), availableLanguages[position[0]]);
                LanguageProvider.changeLocale(getContext(), availableLanguages[position[0]]);
                final HomeActivity home = (HomeActivity) getActivity();
                if (home != null) {
                    home.restart();
                }
            }
        }).create().show();
    }

    private void showSelectModDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        final LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_mod, null);
        final String[] selectedMod = {ThemeProvider.getThemMode(getContext())};
        final ImageView dayMod = view.findViewById(R.id.day_mod_button);
        final ImageView nightMod = view.findViewById(R.id.night_mod_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            switch (selectedMod[0]) {
                case ThemeProvider.DAY_MODE:
                    dayMod.setElevation(ENABLE_ELEVATION);
                    break;
                case ThemeProvider.NIGHT_MODE:
                    nightMod.setElevation(ENABLE_ELEVATION);
            }

        }
        final View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                switch (v.getId()) {
                    case R.id.day_mod_button:
                        selectedMod[0] = ThemeProvider.DAY_MODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dayMod.setElevation(ENABLE_ELEVATION);
                            nightMod.setElevation(DISABLE_ELEVATION);
                        }
                        break;
                    case R.id.night_mod_button:
                        selectedMod[0] = ThemeProvider.NIGHT_MODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            nightMod.setElevation(ENABLE_ELEVATION);
                            dayMod.setElevation(DISABLE_ELEVATION);
                        }
                        break;
                }
            }
        };
        dayMod.setOnClickListener(listener);
        nightMod.setOnClickListener(listener);
        dialog.setTitle(getResources().getString(R.string.SELECT_DAY_NIGHT_MOD))
                .setView(view).setPositiveButton(R.string.APPLY_CHANGES, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                ThemeProvider.setThemeMode(getContext(), selectedMod[0]);
                final HomeActivity homeActivity = (HomeActivity) getActivity();
                if (homeActivity != null) {
                    homeActivity.restart();
                }
            }
        }).create().show();
    }

    private static ArrayList<SettingItemModel> getSettingsItem(final Context context) {
        final String[] stringArray = context.getResources().getStringArray(R.array.SETTING_ARRAY);
        final TypedArray iconArray = context.getResources().obtainTypedArray(R.array.SETTING_ICON_ARRAY);
        final ArrayList<SettingItemModel> items = new ArrayList<>();
        for (int index = 0; index < stringArray.length; ++index) {
            items.add(new SettingItemModel(stringArray[index], iconArray.getDrawable(index)));
        }
        return items;
    }
}













