package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;

public interface IYouSoundPresenter {

    void subscribeOnSounds();
    void unSubscribeOnSounds();
    void updateYouSound(SofindModel sofind);
    void saveUpdatedSofind(SofindModel updatedSofind);

    void loadMoreData(int size);

    void updateAditionalItems(FullSofindModel sofind);
}
