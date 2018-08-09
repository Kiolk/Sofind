package com.github.kiolk.sofind.ui.activities.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.providers.UserInfoProvider;
import com.github.kiolk.sofind.ui.activities.base.BaseActivity;
import com.github.kiolk.sofind.ui.fragments.ConfigurationFragment;
import com.github.kiolk.sofind.ui.fragments.LeaveMessage;
import com.github.kiolk.sofind.ui.fragments.WorldSoundsFragment;
import com.github.kiolk.sofind.ui.fragments.createsound.CreateSoundFragment;
import com.github.kiolk.sofind.ui.fragments.profile.ProfileFragment;
import com.github.kiolk.sofind.ui.fragments.yoursounds.YourSoundsFragment;

/**
 * General class what show MainActivity of application. Is View component in MVP architecture
 */
public class HomeActivity extends BaseActivity implements HomeView {

    private final YourSoundsFragment mYouSoundFragment = new YourSoundsFragment();
    private final WorldSoundsFragment mWorldSoundFragment = new WorldSoundsFragment();
    private final CreateSoundFragment mCreateSoundFragment = new CreateSoundFragment();
    private final ConfigurationFragment mConfigurationFragment = new ConfigurationFragment();
    private HomePresenterImpl mPresenter;
    private final ProfileFragment mEdProfileFragment = new ProfileFragment();
    private FloatingActionButton mAddNewSofind;
    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupFloatActionButton();
        setupNavigationView();
        setupToolBar();
        mPresenter = new HomePresenterImpl(this);

    }

    private void setupFloatActionButton() {
        mAddNewSofind = findViewById(R.id.add_new_sofind_fab);
        mAddNewSofind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                setNewSofindDialog();
            }
        });
    }

    private void setNewSofindDialog() {
        final LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_new_sofind, null);
        final EditText userInput = view.findViewById(R.id.edit_text_dialog);
        final AlertDialog.Builder sofindDialogBuilder = new AlertDialog.Builder(this);
        sofindDialogBuilder.setTitle(R.string.NEW_SOUND).setPositiveButton(R.string.SAVE, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                final FullSofindModel sofindModel = new FullSofindModel();
                final String message = userInput.getText().toString();
                if (message.length() > 100 || message.length() < 1) {
                    Toast.makeText(getBaseContext(), R.string.DESCRIPTION_MESSAGE_BODY, Toast.LENGTH_SHORT).show();
                    return;
                }
                sofindModel.setMindMessage(userInput.getText().toString());
                sofindModel.setCreateTime(System.currentTimeMillis());
                sofindModel.setUserId(UserInfoProvider.getUserId(getBaseContext()));
                DataManager.getInstance().updateNewSound(sofindModel, new SimpleResultListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getBaseContext(), "SoundU updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        sofindDialogBuilder.setView(view);
        sofindDialogBuilder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_additional, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_additional, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:
                break;
            case R.id.save_menu_item:
                mCreateSoundFragment.saveNewSofind();
                break;
            case R.id.profile_menu_item:
                mEdProfileFragment.saveEditProfile();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolBar() {
        final Toolbar toolbar = findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        closeFragments();
    }

    private void setupNavigationView() {
        final NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        reloadDrawerLayout();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                closeDrawerLayout();
                if (item.isChecked()) {
                    return false;
                }
                closeFragments();
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.user_sounds_menu_item:
                        showUserSounds();//
                        break;
                    case R.id.world_sounds_menu_item:
                        mAddNewSofind.setVisibility(View.VISIBLE);
                        showFragment(mWorldSoundFragment);
                        mWorldSoundFragment.showUserSounds();
                        break;
                    case R.id.create_new_sounds_menu_item:
                        showFragment(mCreateSoundFragment);
                        mCreateSoundFragment.prepareForm();
                        break;
                    case R.id.edit_profile_menu_item:
                        final AppBarLayout appBar = findViewById(R.id.general_app_bar_layout);
                        appBar.setExpanded(true);
                        showFragment(mEdProfileFragment);
                        mEdProfileFragment.prepareInformation();
                        break;
                    case R.id.setting_menu_item:
                        showFragment(mConfigurationFragment);
                        mConfigurationFragment.setSettingItems();
                        break;
                    case R.id.log_out_menu_item:
                        mPresenter.singOut();
                        break;
                }
                return false;
            }
        });
    }

    private void showUserSounds() {
        mAddNewSofind.setVisibility(View.VISIBLE);
        showFragment(mYouSoundFragment);
        mYouSoundFragment.setUserFilter();
        mYouSoundFragment.showUserSounds();
    }

    private void closeFragments() {
        mAddNewSofind.setVisibility(View.GONE);
        closeFragment(mYouSoundFragment);
        closeFragment(mWorldSoundFragment);
        closeFragment(mCreateSoundFragment);
        closeFragment(mConfigurationFragment);
        closeFragment(mEdProfileFragment);
    }

    private void closeDrawerLayout() {
        final DrawerLayout layout = findViewById(R.id.main_drawer_layout);
        final NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        layout.closeDrawer(navigationView);
    }

    private void closeFragment(final Fragment targetFragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(targetFragment);
        transaction.commit();
    }

    private void showFragment(final Fragment targetFragment) {
        final FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.add(R.id.fragments_container, targetFragment, null);
        transition.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void singOut() {
        finish();
    }

    @Override
    public void reloadDrawerLayout() {
        final NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        final TextView drawerTitle = navigationView.getHeaderView(0).findViewById(R.id.user_name_header_text_view);
        drawerTitle.setText(UserInfoProvider.getUserNameSurname(getBaseContext()));
    }

    @Override
    public void onBackPressed() {
        new LeaveMessage().show(getFragmentManager(), null);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isFirstLoad) {
            showUserSounds();
            isFirstLoad = false;
        }
    }

    public void restart() {
        final Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
















