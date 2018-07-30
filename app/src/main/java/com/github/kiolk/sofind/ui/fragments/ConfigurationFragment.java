package com.github.kiolk.sofind.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.SimpleArrayAdapter;
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
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Selected Accent color", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Selected mode", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
