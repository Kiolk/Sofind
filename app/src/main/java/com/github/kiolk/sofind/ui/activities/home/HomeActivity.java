package com.github.kiolk.sofind.ui.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.ui.activities.SplashActivity;
import com.github.kiolk.sofind.ui.activities.base.BaseActivity;
import com.github.kiolk.sofind.ui.fragments.ConfigurationFragment;
import com.github.kiolk.sofind.ui.fragments.CreateSoundFragment;
import com.github.kiolk.sofind.ui.fragments.profile.ProfileFragment;
import com.github.kiolk.sofind.ui.fragments.WorldSoundsFragment;
import com.github.kiolk.sofind.ui.fragments.YourSoundsFragment;

public class HomeActivity extends BaseActivity implements HomeView{

    private YourSoundsFragment mYouSoundFragment = new YourSoundsFragment();
    private WorldSoundsFragment mWorldSoundFragment = new WorldSoundsFragment();
    private CreateSoundFragment mCreateSoundFragment = new CreateSoundFragment();
    private ConfigurationFragment mConfigurationFragment = new ConfigurationFragment();
    private HomePresenterImpl mPresenter;
    private ProfileFragment mEdProfileFragment = new ProfileFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNavigationView();
        setupToolBar();
        mPresenter = new HomePresenterImpl(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_additional, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_additional, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:
                Toast.makeText(getBaseContext(), "Selected search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_menu_item:
                Toast.makeText(getBaseContext(), "Selected save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile_menu_item:
                mEdProfileFragment.saveEditProfile();
                Toast.makeText(getBaseContext(), "Selected save", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        closeFragments();
        showFragment(mYouSoundFragment);
    }


    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                closeFragments();
                closeDrawerLayout();
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.user_sounds_menu_item:
                        Toast.makeText(getBaseContext(), "Selected user sounds", Toast.LENGTH_SHORT).show();
                        showFragment(mYouSoundFragment);
                        break;
                    case R.id.world_sounds_menu_item:
                        showFragment(mWorldSoundFragment);
                        Toast.makeText(getBaseContext(), "Selected world sounds", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.create_new_sounds_menu_item:
                        showFragment(mCreateSoundFragment);
                        Toast.makeText(getBaseContext(), "Selected create user sound", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.edit_profile_menu_item:
                        showFragment(mEdProfileFragment);
                        mEdProfileFragment.prepareInformation();
                        Toast.makeText(getBaseContext(), "Selected edit profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting_menu_item:
                        showFragment(mConfigurationFragment);
                        mConfigurationFragment.setSettingItems();
                        Toast.makeText(getBaseContext(), "Selected_settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.log_out_menu_item:
                        mPresenter.singOut();
                        Toast.makeText(getBaseContext(), "Selected log out", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void closeFragments() {
        closeFragment(mYouSoundFragment);
        closeFragment(mWorldSoundFragment);
        closeFragment(mCreateSoundFragment);
        closeFragment(mConfigurationFragment);
        closeFragment(mEdProfileFragment);
    }

    private void closeDrawerLayout() {
        DrawerLayout layout = findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        layout.closeDrawer(navigationView);
    }

    private void closeFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(targetFragment);
        transaction.commit();
    }

    private void showFragment(Fragment targetFragment) {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.add(R.id.fragments_container, targetFragment, null);
        transition.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void singOut() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPresenter.onResupe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }
}
