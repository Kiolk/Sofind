package com.github.kiolk.sofind.ui.fragments.createsound;

import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.providers.UserInfoProvider;

/**
 * Presenter what implement interface ICreateSoundPresenter
 */
public class CreateSoundPresenter implements ICreateSoundPresenter {

    private final ICreateSoundView mView;
    private SofindModel mNewSofind;
    private String mUserName;

    CreateSoundPresenter(final ICreateSoundView createSoundView) {
        mView = createSoundView;
    }

    @Override
    public void updateExampleForm() {
        if (mUserName == null) {
            mUserName = UserInfoProvider.getUserNameSurname(mView.getContext());
        }
        if (mNewSofind == null) {
            mNewSofind = new SofindModel();
            mNewSofind.setUserId(UserInfoProvider.getUserId(mView.getContext()));
            mNewSofind.setCreateTime(System.currentTimeMillis());
        }
        mNewSofind.setMindMessage(mView.getSofindBody());
        mView.updateExample(mNewSofind, mUserName);
    }

    @Override
    public void saveNewSofind(final String sofindBody) {
        mNewSofind.setMindMessage(sofindBody);
        final FullSofindModel fullSofindModel = new FullSofindModel();
        fullSofindModel.setUserId(UserInfoProvider.getUserId(mView.getContext()));
        fullSofindModel.setMindMessage(sofindBody);
        fullSofindModel.setCreateTime(System.currentTimeMillis());
        DataManager.getInstance().updateNewSound(fullSofindModel, new SimpleResultListener() {

            @Override
            public void onSuccess() {
                mView.successSave();
            }

            @Override
            public void onError() {
                mView.error();
            }
        });
    }
}
