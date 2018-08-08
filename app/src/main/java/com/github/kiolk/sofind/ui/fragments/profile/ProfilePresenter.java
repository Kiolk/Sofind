package com.github.kiolk.sofind.ui.fragments.profile;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.providers.UserInfoProvider;

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView mProfileView;
    private UserModel mUserInformation;

    public ProfilePresenter(IProfileView profileView) {
        mProfileView = profileView;
    }

    @Override
    public void saveUser(final UserModel user) {
        if(user.getPassword().equals("")){
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
            public void onError(String message) {

            }
        });
    }

    @Override
    public void getUserInformation() {
        mProfileView.showProgressBar(true);
        DataManager.getInstance().getUserInformation(new ObjectResultListener() {
            @Override
            public void resultProcess(Object result) {
                if (result != null && result instanceof UserModel) {
                    mProfileView.showProgressBar(false);
                    mUserInformation = (UserModel) result;
                    mProfileView.setInformation(mUserInformation);
                }
            }
        });

    }

    @Override
    public boolean confirmUserPassword(String password) {
        return password.equals(mUserInformation.getPassword());
    }


}
