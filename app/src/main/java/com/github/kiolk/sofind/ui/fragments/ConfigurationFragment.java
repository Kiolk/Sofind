package com.github.kiolk.sofind.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.adapters.SimpleArrayAdapter;
import com.github.kiolk.sofind.providers.LanguageProvider;
import com.github.kiolk.sofind.providers.ThemeProvider;
import com.github.kiolk.sofind.ui.activities.home.HomeActivity;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;
import com.github.kiolk.sofind.util.ResoursesUtil;

public class ConfigurationFragment extends BaseFragment {

    private void changeTitle() {
        super.titleResource = R.string.SETTING;
    }

    private void changeMenu() {
        super.menuId = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        changeTitle();
        changeMenu();
        setupToolBar();
        View view = inflater.inflate(R.layout.fragment_with_list_view, null);
        return view;
    }

    public void setSettingItems() {
        SimpleArrayAdapter adapter = new SimpleArrayAdapter(getContext(), R.layout.layout_setting_item, ResoursesUtil.getSettingsItem(getContext()));
        ListView list = getView().findViewById(R.id.setting_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), "Selected language", Toast.LENGTH_SHORT).show();
                        showSelectLanguageDialog();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Selected Accent color", Toast.LENGTH_SHORT).show();
                        showSelectAccentColor();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Selected mode", Toast.LENGTH_SHORT).show();
                        showSelectModDialog();
                        break;
                }
            }
        });
    }

    private void showSelectAccentColor() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_accent_color, null);
        final String[] accentColor = {ThemeProvider.getAccentColor(getContext())};
        final FloatingActionButton redButton = view.findViewById(R.id.red_fab);
        final FloatingActionButton blueButton = view.findViewById(R.id.blue_fab);
        final FloatingActionButton greenButton = view.findViewById(R.id.green_fab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (accentColor[0]) {
                case ThemeProvider.RED_ACCENT:
                    redButton.setElevation(5.0f);
                    break;
                case ThemeProvider.BLUE_ACCENT:

                    blueButton.setElevation(5.0f);
                    break;
                case ThemeProvider.GREEN_ACCENT:
                    greenButton.setElevation(5.0f);
                    break;
            }
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            greenButton.setElevation(5.0f);
                        }
                        accentColor[0] = ThemeProvider.GREEN_ACCENT;
                        break;
                }
            }

            private void deselectElevationButton() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    blueButton.setElevation(1.0f);
                    redButton.setElevation(1.0f);
                    greenButton.setElevation(1.0f);
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
                    public void onClick(DialogInterface dialog, int which) {
                        ThemeProvider.setAccentColor(getContext(), accentColor[0]);
                        HomeActivity home = (HomeActivity) getActivity();
                        home.restart();
                    }
                })
                .create()
                .show();
    }

    private void showSelectLanguageDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        String currentLanguage = LanguageProvider.getLanguage(getContext());
        final String[] availableLanguages = getResources().getStringArray(R.array.LANGUAGES);
        String[] fullNameLanguages = getResources().getStringArray(R.array.LANGUAGES_FULL_NAME);
        final int[] position = {1};

        for (int index = 0; index < availableLanguages.length; ++index) {
            if (currentLanguage.equals(availableLanguages[index])) {
                position[0] = index;
            }
        }

        dialog.setTitle(R.string.CHOSE_LANGUAGE).setSingleChoiceItems(fullNameLanguages, position[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                position[0] = which;
            }
        }).setPositiveButton(R.string.APPLY_CHANGES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LanguageProvider.setLanguage(getContext(), availableLanguages[position[0]]);
                LanguageProvider.changeLocale(getContext(), availableLanguages[position[0]]);
                HomeActivity home = (HomeActivity) getActivity();
                home.restart();
            }
        }).create().show();
    }

    private void showSelectModDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_mod, null);
        final String[] selectedMod = {ThemeProvider.getThemMode(getContext())};
        final ImageView dayMod = view.findViewById(R.id.day_mod_button);
        final ImageView nightMod = view.findViewById(R.id.night_mod_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            switch (selectedMod[0]) {
                case ThemeProvider.DAY_MODE:
                    dayMod.setElevation(5.0f);
                    break;
                case ThemeProvider.NIGHT_MODE:
                    nightMod.setElevation(5.0f);
            }

        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.day_mod_button:
                        selectedMod[0] = ThemeProvider.DAY_MODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dayMod.setElevation(5.0f);
                            nightMod.setElevation(1.0F);
                        }
                        break;
                    case R.id.night_mod_button:
                        selectedMod[0] = ThemeProvider.NIGHT_MODE;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            nightMod.setElevation(5.0F);
                            dayMod.setElevation(1.0f);
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
            public void onClick(DialogInterface dialog, int which) {
                ThemeProvider.setThemeMode(getContext(), selectedMod[0]);
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.restart();
            }
        }).create().show();
    }
}













