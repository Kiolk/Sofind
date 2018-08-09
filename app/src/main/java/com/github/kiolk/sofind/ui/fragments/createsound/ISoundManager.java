package com.github.kiolk.sofind.ui.fragments.createsound;

import com.github.kiolk.sofind.data.listeners.ObjectResultListener;
import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;

/**
 * Interface of model component for working with sofind on backend side. Contain methods for management
 * of manipulation with sofinds.
 */
public interface ISoundManager {

    void updateNewSound(FullSofindModel sofind, SimpleResultListener listener);

    void subscribeOnUsersSounds(IYouSoundPresenter presenter);

    void unSubscribeOnUsersSounds();

    void getUserFullName(String userId, ObjectResultListener listener);

    void updateSound(SofindModel updatedSofind);

    void loadMoreSofinds(IYouSoundPresenter presenter, int getAdditional);
}

