package com.github.kiolk.sofind.ui.fragments.profile;

import com.github.kiolk.sofind.data.models.UserModel;

public interface IProfilePresenter {

    void saveUser(UserModel user);

    void getUserInformation();
}
