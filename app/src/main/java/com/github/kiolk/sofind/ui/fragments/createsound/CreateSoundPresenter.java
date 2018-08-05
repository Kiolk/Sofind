package com.github.kiolk.sofind.ui.fragments.createsound;

import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.providers.UserInfoProvider;

public class CreateSoundPresenter implements ICreateSoundPresenter {

    private ICreateSoundView mView;
    private SofindModel mNewSofind;
    private String mUserName;

    CreateSoundPresenter(ICreateSoundView createSoundView){
        mView = createSoundView;
//        mNewSofind = new SofindModel();
    }

    @Override
    public void updateExampleForm() {
        if(mUserName == null) {
            mUserName = UserInfoProvider.getUserNameSurname(mView.getContext());
        }
        if(mNewSofind == null){
            mNewSofind = new SofindModel();
            mNewSofind.setUserid(UserInfoProvider.getUserId(mView.getContext()));
            mNewSofind.setCreateTime(System.currentTimeMillis());
        }
        mNewSofind.setMindMessage(mView.getSofindBody());
        mView.updateExample(mNewSofind, mUserName);
    }

    @Override
    public void saveNewSofind(String sofindBody) {
        mNewSofind.setMindMessage(sofindBody);
        DataManager.getInstance().updateNewSound(mNewSofind, new SimpleResultListener() {
            @Override
            public void onSuccess() {
                mView.succesSave();
            }

            @Override
            public void onError(String message) {
                mView.error();
            }
        });
    }
}
