package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.models.FullSofindModel;

/**
 * Interface of view component for displaying sofinds
 */
public interface IYouSoundView {

    void showUserSounds();

    void setUserSound(FullSofindModel userSofind);

    void addLateSound(FullSofindModel userSofind);

    void setUserFilter();

    void showUpdateProgressBar(boolean isShow);

    void resetAddPoint();
}
