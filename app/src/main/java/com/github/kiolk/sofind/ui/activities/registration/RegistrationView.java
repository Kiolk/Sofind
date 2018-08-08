package com.github.kiolk.sofind.ui.activities.registration;

import com.github.kiolk.sofind.data.models.UserModel;

public interface RegistrationView {

    int INVALID_PASSWORD_OR_EMAIL = 0;
    int NOT_CORRECT_COMPLETE_FORM = 1;

    void showProgressBar(boolean isShow);

    void success();

    void error(int message);

    boolean isValidEmailAndPassword();

    boolean isValidRegistrationForm();

    void showMistake();

    UserModel getNewUserInformation();
}
