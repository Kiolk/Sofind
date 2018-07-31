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

public abstract class BaseFragment extends Fragment {

    protected int titleResource;

    protected int menuId;
//    int menuId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        setupToolBar();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setupToolBar(){
        final HomeActivity activity = (HomeActivity) getActivity();
        Toolbar toolbar = activity.findViewById(R.id.main_tool_bar);
        if(toolbar == null){
            toolbar = activity.findViewById(R.id.main_tool_bar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DrawerLayout drawerLayout = activity.findViewById(R.id.main_drawer_layout);
               drawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar.setTitle(titleResource);
        Menu menu = toolbar.getMenu();
        if(menu != null) {
            MenuItem item = menu.findItem(menuId);
            if(menuId == 0){
                resetMenuIcon(menu);
            }
            if (item != null) {
                resetMenuIcon(menu);
                menu.findItem(menuId).setVisible(true);
//               if(menuId != R.id.save_menu_item){
//                   menu.findItem(R.id.save_menu_item).setVisible(false);
//               }else{
//                   menu.findItem(R.id.search_menu_item).setVisible(false);
//               }
            }
//        }else{
//
//        }
        }
    }

    private void resetMenuIcon(Menu menu) {
        menu.findItem(R.id.save_menu_item).setVisible(false);
        menu.findItem(R.id.search_menu_item).setVisible(false);
    }
}
