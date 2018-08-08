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
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.providers.UserInfoProvider;
import com.github.kiolk.sofind.ui.activities.SplashActivity;
import com.github.kiolk.sofind.ui.activities.base.BaseActivity;
import com.github.kiolk.sofind.ui.fragments.ConfigurationFragment;
import com.github.kiolk.sofind.ui.fragments.LeaveMessage;
import com.github.kiolk.sofind.ui.fragments.createsound.CreateSoundFragment;
import com.github.kiolk.sofind.ui.fragments.profile.ProfileFragment;
import com.github.kiolk.sofind.ui.fragments.WorldSoundsFragment;
import com.github.kiolk.sofind.ui.fragments.yoursounds.YourSoundsFragment;

import java.util.zip.Inflater;

public class HomeActivity extends BaseActivity implements HomeView{

    private YourSoundsFragment mYouSoundFragment = new YourSoundsFragment();
    private WorldSoundsFragment mWorldSoundFragment = new WorldSoundsFragment();
    private CreateSoundFragment mCreateSoundFragment = new CreateSoundFragment();
    private ConfigurationFragment mConfigurationFragment = new ConfigurationFragment();
    private HomePresenterImpl mPresenter;
    private ProfileFragment mEdProfileFragment = new ProfileFragment();
    private FloatingActionButton mAddNewSofind;
    private  boolean isFirstLoad = true;
    private boolean isRedyToLeave = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
            public void onClick(View v) {
                setNewSofindDialog();
            }
        });
    }

    private void setNewSofindDialog() {
       LayoutInflater inflater  = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_sofind, null);
        final EditText userInput = view.findViewById(R.id.edit_text_dialog);
//        userInput.setli
        AlertDialog.Builder sofindDialogBuilder = new AlertDialog.Builder(this);
        sofindDialogBuilder.setTitle(R.string.NEW_SOUND).setPositiveButton(R.string.SAVE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FullSofindModel sofindModel = new FullSofindModel();
                String message = userInput.getText().toString();
                if(message.length() > 100 || message.length() < 1 ){
                    Toast.makeText(getBaseContext(), R.string.DESCRIPTION_MESSAGE_BODY, Toast.LENGTH_SHORT).show();
                    return;
                }
                sofindModel.setMindMessage(userInput.getText().toString());
                sofindModel.setCreateTime(System.currentTimeMillis());
                sofindModel.setUserid(UserInfoProvider.getUserId(getBaseContext()));
               DataManager.getInstance().updateNewSound(sofindModel, new SimpleResultListener() {
                   @Override
                   public void onSuccess() {
                       Toast.makeText(getBaseContext(), "SoundU updated", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onError(String message) {

                   }
               });
            }
        });
        sofindDialogBuilder.setView(view);
        sofindDialogBuilder.create().show();
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
                mCreateSoundFragment.saveNewSofind();
                Toast.makeText(getBaseContext(), "Selected save", Toast.LENGTH_SHORT).show();
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
        Toolbar toolbar = findViewById(R.id.main_tool_bar);
//        Toolbar.LayoutParams params = (Toolbar.LayoutParams) toolbar.getLayoutParams();
//        params.layoutS
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        closeFragments();
//        showFragment(mYouSoundFragment);
//        showUserSounds();
    }


    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        reloadDrawerLayout();
//        TextView drawerTitle = navigationView.getHeaderView(0).findViewById(R.id.user_name_header_text_view);
//        drawerTitle.setText(UserInfoProvider.getUserNameSurname(getBaseContext()));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                closeDrawerLayout();
                if(item.isChecked()){
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
                        Toast.makeText(getBaseContext(), "Selected world sounds", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.create_new_sounds_menu_item:
                        showFragment(mCreateSoundFragment);
                        mCreateSoundFragment.prepareForm();
                        Toast.makeText(getBaseContext(), "Selected create user sound", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.edit_profile_menu_item:
                        AppBarLayout appBar = findViewById(R.id.general_app_bar_layout);
                        appBar.setExpanded(true);

                        FrameLayout frame = findViewById(R.id.fragments_container);
//                        frame.setBe
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

    private void showUserSounds() {
        mAddNewSofind.setVisibility(View.VISIBLE);
//        Toast.makeText(getBaseContext(), "Selected user sounds", Toast.LENGTH_SHORT).show();
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
//        Intent intent = new Intent(this, SplashActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void reloadDrawerLayout() {
        NavigationView navigationView = findViewById(R.id.menu_navigation_view);
        TextView drawerTitle = navigationView.getHeaderView(0).findViewById(R.id.user_name_header_text_view);
        drawerTitle.setText(UserInfoProvider.getUserNameSurname(getBaseContext()));
    }

    @Override
    public void onBackPressed() {
        if(isRedyToLeave){
//            super.onBackPressed();
//            Intent intent = new Intent(this, SplashActivity.class);
//            startActivity(intent);
            finish();
        }else {
            new LeaveMessage().show(getFragmentManager(), null);
        }
////        showUserSounds();
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getBaseContext());
//        alertBuilder.setTitle(R.string.ARE_YOU_WANT_LEAV).setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//               isRedyToLeave = true;
//               onBackPressed();
//            }
//        }).setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //
//            }
//        }).create().show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPresenter.onResupe();
        if(isFirstLoad){
            showUserSounds();
            isFirstLoad = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    public void restart(){
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
















