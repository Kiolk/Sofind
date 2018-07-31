package com.github.kiolk.sofind.ui.activities.registration;

public interface Presenter {

    void singIn(String email, String password);

    void registerNewUser(String email, String password);
}
