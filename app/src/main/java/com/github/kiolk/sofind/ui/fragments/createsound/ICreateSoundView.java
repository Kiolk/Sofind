package com.github.kiolk.sofind.ui.fragments.createsound;

import android.content.Context;

import com.github.kiolk.sofind.data.models.SofindModel;

/**
 * Interface of view component in create new sofind item process
 */

public interface ICreateSoundView {

    void prepareForm();

    void updateExample(SofindModel sofind, String mUserName);

    void saveNewSofind();

    void clearForm();

    void successSave();

    Context getContext();

    String getSofindBody();

    void error();
}
