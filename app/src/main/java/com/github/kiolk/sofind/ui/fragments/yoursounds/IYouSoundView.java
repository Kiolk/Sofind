package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;

public interface IYouSoundView {

    void showUserSounds();
    void setUserSound(FullSofindModel userSofind);
    void addLateSound(FullSofindModel userSofind);

    void setUserFilter();
    void shouUpdateProgressBar(boolean isShow);

    void resetAddPoint();
}
