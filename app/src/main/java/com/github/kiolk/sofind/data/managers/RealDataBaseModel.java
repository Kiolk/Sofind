package com.github.kiolk.sofind.data.managers;

import com.github.kiolk.sofind.data.models.UserModel;

import java.util.List;

public interface RealDataBaseModel {

    void saveNewUser();
    List<UserModel> getAllUsers();
}
