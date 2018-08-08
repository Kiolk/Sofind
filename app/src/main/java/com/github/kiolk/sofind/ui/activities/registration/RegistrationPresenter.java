package com.github.kiolk.sofind.ui.activities.registration;

/**
 * Interface of Presenter component of registration process
 */
public interface RegistrationPresenter {

    void singIn(String email, String password);

    void registerNewUser(String email, String password);
}
