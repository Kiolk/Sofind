package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;

public class YouSoundPresenter implements IYouSoundPresenter {

    private IYouSoundView mView;

    public YouSoundPresenter(IYouSoundView view){
        mView = view;
    }
    @Override
    public void subscribeOnSounds() {
        DataManager.getInstance().subscribeOnUsersSounds(this);
    }

    @Override
    public void unSubscribeOnSounds() {
        DataManager.getInstance().unSubscribeOnUsersSounds();
    }

    @Override
    public void updateYouSound(SofindModel sofind) {
        final FullSofindModel fullSofind = new FullSofindModel(sofind);
        DataManager.getInstance().getUserFullName(sofind.getUserid(), new ObjectResultListener() {
            @Override
            public void resultProcess(Object result) {
                String fullName = (String) result;
                fullSofind.setUserFullName(fullName);
                mView.setUserSound(fullSofind);
            }
        });
    }

    @Override
    public void saveUpdatedSofind(SofindModel updatedSofind) {
        DataManager.getInstance().updateSound(updatedSofind);
    }
}
