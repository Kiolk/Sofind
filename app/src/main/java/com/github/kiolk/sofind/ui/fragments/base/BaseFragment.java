package com.github.kiolk.sofind.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.ui.activities.home.HomeActivity;

/**
 * Common logic for all fragments what extend from this abstract class
 */
public abstract class BaseFragment extends Fragment {

    protected int titleResource;

    protected int menuId;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setupToolBar() {
        final HomeActivity activity = (HomeActivity) getActivity();
        Toolbar toolbar = activity.findViewById(R.id.main_tool_bar);
        if (toolbar == null) {
            toolbar = activity.findViewById(R.id.main_tool_bar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final DrawerLayout drawerLayout = activity.findViewById(R.id.main_drawer_layout);
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar.setTitle(titleResource);
        final Menu menu = toolbar.getMenu();
        if (menu != null) {
            final MenuItem item = menu.findItem(menuId);
            if (menuId == 0) {
                resetMenuIcon(menu);
            }
            if (item != null) {
                resetMenuIcon(menu);
                menu.findItem(menuId).setVisible(true);
            }
        }
    }

    private void resetMenuIcon(final Menu menu) {
        menu.findItem(R.id.save_menu_item).setVisible(false);
        menu.findItem(R.id.search_menu_item).setVisible(false);
        menu.findItem(R.id.profile_menu_item).setVisible(false);
    }
}
