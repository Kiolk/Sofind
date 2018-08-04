package com.github.kiolk.sofind.data.managers;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.UserModel;

import java.util.List;

public interface RealDataBaseModel {

    void getUserInformation(ObjectResultListener listener);

    List<UserModel> getAllUsers();
}
