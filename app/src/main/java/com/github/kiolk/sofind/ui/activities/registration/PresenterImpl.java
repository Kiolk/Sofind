package com.github.kiolk.sofind.ui.activities.registration;

import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.UserModel;

public class PresenterImpl implements Presenter {

    private RegistrationView mRegistrationView;

    PresenterImpl(RegistrationView view){
        mRegistrationView = view;
    }

    @Override
    public void singIn(String email, String password) {
        mRegistrationView.showProgressBar(true);
        if(mRegistrationView.isValidEmailAndPassword()){
            DataManager.getInstance().userLogin(email, password, new SimpleResultListener() {
                @Override
                public void onSuccess() {
                    mRegistrationView.showProgressBar(false);
                    mRegistrationView.success();
                }

                @Override
                public void onError(String message) {
                    mRegistrationView.showProgressBar(false);
                    mRegistrationView.error(1);
                }
            });
        }else{
            mRegistrationView.showMistake();
            mRegistrationView.error(RegistrationView.INVALID_PASSWORD_OR_EMAIL);
            mRegistrationView.showProgressBar(false);
        }

    }

    @Override
    public void registerNewUser(String email, String password) {
        if(mRegistrationView.isValidRegistrationForm()){
            DataManager.getInstance().registerNewUser(email, password, new SimpleResultListener() {
                @Override
                public void onSuccess() {
                    UserModel user = mRegistrationView.getNewUserInformation();
                    user.setUserId(DataManager.getInstance().getUserUid());
                    DataManager.getInstance().saveNewUser(user, new SimpleResultListener(){
                        @Override
                        public void onSuccess() {
                            mRegistrationView.showProgressBar(false);
                            mRegistrationView.success();
                        }

                        @Override
                        public void onError(String message) {
                            mRegistrationView.showProgressBar(false);
                            mRegistrationView.error(1);
                        }
                    });
                }

                @Override
                public void onError(String message) {
                    mRegistrationView.showProgressBar(false);
                    mRegistrationView.error(1);
                }
            });
        }else{
            mRegistrationView.showMistake();
            mRegistrationView.error(RegistrationView.NOT_CORRECT_COMPLET_FORM);
            mRegistrationView.showProgressBar(false);
        }
    }
}
