package com.github.kiolk.sofind.ui.fragments.profile;

import com.github.kiolk.sofind.data.listeners.ObjectResultListener;
import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.UserModel;

/**
 * Implementation of presenter interface
 */

public class ProfilePresenter implements IProfilePresenter {

    private final IProfileView mProfileView;
    private UserModel mUserInformation;

    ProfilePresenter(final IProfileView profileView) {
        mProfileView = profileView;
    }

    @Override
    public void saveUser(final UserModel user) {
        if ("".equals(user.getPassword())) {
            user.setPassword(mUserInformation.getPassword());
        }
        user.setUserId(mUserInformation.getUserId());
        DataManager.getInstance().saveNewUser(user, new SimpleResultListener() {

            @Override
            public void onSuccess() {
                mProfileView.successUpdate();
                DataManager.getInstance().changeUserFullName(user.getUserId(), user.getUserName() + " " + user.getSurname());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void getUserInformation() {
        mProfileView.showProgressBar(true);
        DataManager.getInstance().getUserInformation(new ObjectResultListener() {

            @Override
            public void resultProcess(final Object result) {
                if (result instanceof UserModel) {
                    mProfileView.showProgressBar(false);
                    mUserInformation = (UserModel) result;
                    mProfileView.setInformation(mUserInformation);
                }
            }
        });

    }

    @Override
    public boolean confirmUserPassword(final String password) {
        return password.equals(mUserInformation.getPassword());
    }

}
