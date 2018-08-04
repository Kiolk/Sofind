package com.github.kiolk.sofind.ui.fragments.profile;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.UserModel;

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView mProfileView;

    public ProfilePresenter(IProfileView profileView) {
        mProfileView = profileView;
    }

    @Override
    public void saveUser(UserModel user) {
        DataManager.getInstance().saveNewUser(user, new SimpleResultListener() {
            @Override
            public void onSuccess() {
                mProfileView.successUpdate();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void getUserInformation() {
        DataManager.getInstance().getUserInformation(new ObjectResultListener() {
            @Override
            public void resultProcess(Object result) {
                if (result != null && result instanceof UserModel) {
                    mProfileView.setInformation((UserModel) result);
                }
            }
        });

    }
}
