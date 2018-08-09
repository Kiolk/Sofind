package com.github.kiolk.sofind.ui.fragments.profile;

import com.github.kiolk.sofind.data.models.UserModel;

/**
 * Interface of presenter component in display and edit profile information
 */

public interface IProfilePresenter {

    /**
     * Call for save new user on backend
     * @param user - UserModel object with information for update.
     */
    void saveUser(UserModel user);

    /**
     * Get current information about user from backend
     */
    void getUserInformation();

    /**
     * Method for checking correct inputted password with current user password for updating profile
     * @param password - inputted user password
     * @return true - if inputted password is same as current password of user
     */
    boolean confirmUserPassword(String password);

}
