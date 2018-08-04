package com.github.kiolk.sofind.ui.activities.registration;

import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.UserModel;

public interface RegistrationModel {

    void userLogin(String email, String password, SimpleResultListener listener);

    void registerNewUser(String email, String password, SimpleResultListener simpleResultListener);

    void addStateListener(SimpleResultListener listener);

    void removeStateListener();

    void signOut();

    String getUserUid();

    void saveNewUser(UserModel user, SimpleResultListener listener);
}
