package com.github.kiolk.sofind.ui.activities.home;

import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;

/**
 * RegistrationPresenter implementation for HomeView
 */
public class HomePresenterImpl implements HomePresenter {

    private final HomeView mHomeView;

    HomePresenterImpl(final HomeView view) {
        mHomeView = view;
    }

//    @Override
//    public void onResupe() {
//        DataManager.getInstance().addStateListener(new SimpleResultListener() {
//            @Override
//            public void onSuccess() {
//                mHomeView.singOut();
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
//    }
//
//    @Override
//    public void onPause() {
//        DataManager.getInstance().removeStateListener();
//    }

    @Override
    public void singOut() {
        DataManager.getInstance().signOut(new SimpleResultListener() {

            @Override
            public void onSuccess() {
                mHomeView.singOut();
            }

            @Override
            public void onError() {

            }
        });
    }
}
