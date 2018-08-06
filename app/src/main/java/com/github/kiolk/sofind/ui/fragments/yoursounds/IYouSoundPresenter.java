package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.models.SofindModel;

public interface IYouSoundPresenter {

    void subscribeOnSounds();
    void unSubscribeOnSounds();
    void updateYouSound(SofindModel sofind);
    void saveUpdatedSofind(SofindModel updatedSofind);
}
