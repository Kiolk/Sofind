package com.github.kiolk.sofind.ui.fragments.profile;

import com.github.kiolk.sofind.data.models.UserModel;

public interface IProfileView {

    void saveEditProfile();

    void setInformation(UserModel user);

    void setupConfirmPasswordDialog();

    void successUpdate();

    void showProgressBar(boolean isShow);

    void showErrorMessage(int message);

    boolean checkCorrectInputInfo();
}
