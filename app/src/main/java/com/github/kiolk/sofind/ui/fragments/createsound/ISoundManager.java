package com.github.kiolk.sofind.ui.fragments.createsound;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;

public interface ISoundManager {

    void updateNewSound(SofindModel sofind, SimpleResultListener listener);

    void subscribeOnUsersSounds(IYouSoundPresenter presenter);

    void unSubscribeOnUsersSounds();

    void getUserFullName(String userId, ObjectResultListener listener);

    void updateSound(SofindModel updatedSofind);
}

