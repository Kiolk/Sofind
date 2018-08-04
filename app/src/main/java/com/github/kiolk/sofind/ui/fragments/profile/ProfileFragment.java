package com.github.kiolk.sofind.ui.fragments.profile;

import android.os.Bundle;
import android.os.TokenWatcher;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.fragments.base.BaseFragment;
import com.github.kiolk.sofind.ui.fragments.profile.IProfileView;
import com.github.kiolk.sofind.ui.fragments.profile.ProfilePresenter;

public class ProfileFragment extends BaseFragment implements IProfileView {

    private ProfilePresenter mPresenter = new ProfilePresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    changeMenu();
     changeTitle();
        setupToolBar();
            View view = inflater.inflate(R.layout.fragment_profile, null);
        return view;
    }

    private void changeTitle() {
        super.titleResource = R.string.EDIT_PROFILE;
    }

    private void changeMenu() {
        super.menuId = R.id.profile_menu_item;
    }

    @Override
    public void saveEditProfile() {
        mPresenter.saveUser(null);
    }

   public void prepareInformation(){
        mPresenter.getUserInformation();
    }

    @Override
    public void setInformation(UserModel user) {
        Toast.makeText(getContext(), "User information" + user.getUserId(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUpdate() {
        Toast.makeText(getContext(), "Success save information", Toast.LENGTH_SHORT).show();
    }
}
