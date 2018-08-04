package com.github.kiolk.sofind.ui.activities.home;

import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;

public class HomePresenterImpl implements HomePresenter {

    private HomeView mHomeView;

    protected HomePresenterImpl(HomeView view){
        mHomeView = view;
    }

    @Override
    public void onResupe() {
        DataManager.getInstance().addStateListener(new SimpleResultListener() {
            @Override
            public void onSuccess() {
                mHomeView.singOut();
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    public void onPause() {
        DataManager.getInstance().removeStateListener();
    }

    @Override
    public void singOut() {
        DataManager.getInstance().signOut();
    }
}
