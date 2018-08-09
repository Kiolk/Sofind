package com.github.kiolk.sofind.ui.fragments.yoursounds;

import com.github.kiolk.sofind.data.listeners.ObjectResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;

/**
 * Implementation of IYouSoundPresenter for display only users sofinds with possibility of pagination of data
 */
public class YouSoundPresenter implements IYouSoundPresenter {

    private static final int PORTION_UPDATE_ITEMS = 10;
    private final IYouSoundView mView;
    private int mAdditionalUpdate = -10;
    private int mCount;

    public YouSoundPresenter(final IYouSoundView view) {
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
    public void updateYouSound(final SofindModel sofind) {
        final FullSofindModel fullSofind = new FullSofindModel(sofind);
        DataManager.getInstance().getUserFullName(sofind.getUserId(), new ObjectResultListener() {

            @Override
            public void resultProcess(final Object result) {
                final String fullName = (String) result;
                fullSofind.setUserFullName(fullName);
                mView.setUserSound(fullSofind);
            }
        });
    }

    @Override
    public void saveUpdatedSofind(final SofindModel updatedSofind) {
        DataManager.getInstance().updateSound(updatedSofind);
    }

    @Override
    public void loadMoreData(final int size) {
        if (mAdditionalUpdate + PORTION_UPDATE_ITEMS == 0) {
            mView.resetAddPoint();
            mView.showUpdateProgressBar(true);
            mAdditionalUpdate = size;
            mCount = PORTION_UPDATE_ITEMS;
            DataManager.getInstance().loadMoreSofinds(this, size + PORTION_UPDATE_ITEMS);
        }
    }

    @Override
    public void updateAdditionalItems(final FullSofindModel sofind) {
        --mAdditionalUpdate;
        --mCount;
        if (mCount > 0) {
            mView.showUpdateProgressBar(false);
            final FullSofindModel fullSofind = new FullSofindModel(sofind);
            DataManager.getInstance().getUserFullName(sofind.getUserId(), new ObjectResultListener() {

                @Override
                public void resultProcess(final Object result) {
                    final String fullName = (String) result;
                    fullSofind.setUserFullName(fullName);
                    mView.addLateSound(fullSofind);
                }
            });
        }
    }
}
