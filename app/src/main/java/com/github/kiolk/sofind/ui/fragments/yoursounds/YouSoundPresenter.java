package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.listeners.ObjectResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;

public class YouSoundPresenter implements IYouSoundPresenter {

    public static final int PORTION_UPDATE_ITEMS = 10;
    private IYouSoundView mView;
    private int mAadditionalUpdate = - 10;
    private int mCount = 0;

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
        DataManager.getInstance().getUserFullName(sofind.getUserId(), new ObjectResultListener() {
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

    @Override
    public void loadMoreData(int size) {
        if(mAadditionalUpdate + PORTION_UPDATE_ITEMS == 0){
            mView.resetAddPoint();
            mView.shouUpdateProgressBar(true);
            mAadditionalUpdate = size;
            mCount = PORTION_UPDATE_ITEMS;
            DataManager.getInstance().loadMoreSofinds(this, size + PORTION_UPDATE_ITEMS);
        }
    }

    @Override
    public void updateAditionalItems(FullSofindModel sofind) {
        --mAadditionalUpdate;
        --mCount;
        if(mCount > 0){
            mView.shouUpdateProgressBar(false);
            final FullSofindModel fullSofind = new FullSofindModel(sofind);
            DataManager.getInstance().getUserFullName(sofind.getUserId(), new ObjectResultListener() {
                @Override
                public void resultProcess(Object result) {
                    String fullName = (String) result;
                    fullSofind.setUserFullName(fullName);
                    mView.addLateSound(fullSofind);
                }
            });
        }
    }
}
