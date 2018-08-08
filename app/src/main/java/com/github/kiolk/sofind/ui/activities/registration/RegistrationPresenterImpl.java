package com.github.kiolk.sofind.ui.activities.registration;

import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.UserModel;

/**
 * Implementation of presenter for registration process
 */
public class RegistrationPresenterImpl implements RegistrationPresenter {

    private final RegistrationView mRegistrationView;

    RegistrationPresenterImpl(final RegistrationView view) {
        mRegistrationView = view;
    }

    @Override
    public void singIn(final String email, final String password) {
        mRegistrationView.showProgressBar(true);
        if (mRegistrationView.isValidEmailAndPassword()) {
            DataManager.getInstance().userLogin(email, password, new SimpleResultListener() {

                @Override
                public void onSuccess() {
                    mRegistrationView.showProgressBar(false);
                    mRegistrationView.success();
                }

                @Override
                public void onError() {
                    mRegistrationView.showProgressBar(false);
                    mRegistrationView.error(1);
                }
            });
        } else {
            mRegistrationView.showMistake();
            mRegistrationView.error(RegistrationView.INVALID_PASSWORD_OR_EMAIL);
            mRegistrationView.showProgressBar(false);
        }

    }

    @Override
    public void registerNewUser(final String email, final String password) {
        if (mRegistrationView.isValidRegistrationForm()) {
            DataManager.getInstance().registerNewUser(email, password, new SimpleResultListener() {

                @Override
                public void onSuccess() {
                    final UserModel user = mRegistrationView.getNewUserInformation();
                    user.setUserId(DataManager.getInstance().getUserUid());
                    DataManager.getInstance().saveNewUser(user, new SimpleResultListener() {

                        @Override
                        public void onSuccess() {
                            mRegistrationView.showProgressBar(false);
                            mRegistrationView.success();
                        }

                        @Override
                        public void onError() {
                            mRegistrationView.showProgressBar(false);
                            mRegistrationView.error(1);
                        }
                    });
                }

                @Override
                public void onError() {
                    mRegistrationView.showProgressBar(false);
                    mRegistrationView.error(1);
                }
            });
        } else {
            mRegistrationView.showMistake();
            mRegistrationView.error(RegistrationView.NOT_CORRECT_COMPLETE_FORM);
            mRegistrationView.showProgressBar(false);
        }
    }
}
