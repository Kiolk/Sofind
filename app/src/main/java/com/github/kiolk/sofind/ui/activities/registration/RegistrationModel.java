package com.github.kiolk.sofind.ui.activities.registration;

import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.models.UserModel;

/**
 * Interface of model component of MVP for registration process
 */
public interface RegistrationModel {

    void userLogin(String email, String password, SimpleResultListener listener);

    void registerNewUser(String email, String password, SimpleResultListener simpleResultListener);

    void signOut(SimpleResultListener simpleResultListener);

    String getUserUid();

    void saveNewUser(UserModel user, SimpleResultListener listener);
}
