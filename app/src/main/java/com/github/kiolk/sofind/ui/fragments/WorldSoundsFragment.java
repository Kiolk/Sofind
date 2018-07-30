package com.github.kiolk.sofind.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;


public class WorldSoundsFragment extends BaseFragment {

//   super.titleResource = R.string.YOUR_SOUNDS;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_recycler_view, null);
        changeTitle();
        changeMenu();
        setupToolBar();
        return view;
    }
    //TODO work with inheritance fields
    private void changeMenu() {
        super.menuId = R.id.search_menu_item;
    }

    private void changeTitle() {
        super.titleResource = R.string.WORLD_SOUNDS;
    }
}
