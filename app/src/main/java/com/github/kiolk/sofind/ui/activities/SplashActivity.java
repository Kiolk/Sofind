package com.github.kiolk.sofind.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.listeners.ObjectResultListener;
import com.github.kiolk.sofind.data.managers.DataManager;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.providers.LanguageProvider;
import com.github.kiolk.sofind.providers.PrefGetter;
import com.github.kiolk.sofind.providers.UserInfoProvider;
import com.github.kiolk.sofind.ui.activities.base.BaseActivity;
import com.github.kiolk.sofind.ui.activities.home.HomeActivity;
import com.github.kiolk.sofind.ui.activities.registration.RegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity start first and check logged user or not
 */
public class SplashActivity extends BaseActivity {

    private static final int RC_REGISTRATION = 0;

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        checkLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkAuthentication();
    }

    private void checkLocale() {
        String language = LanguageProvider.getLanguage(getBaseContext());
        if (language.equals(PrefGetter.DEFAULT_VALUE)) {
            language = LanguageProvider.LANGUAGE_EN;
        }
        LanguageProvider.changeLocale(getBaseContext(), language);
    }

    private void checkAuthentication() {
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startHomeActivity();
                } else {
                    startRegistrationPage();
                }
            }
        };
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_REGISTRATION) {
            if (resultCode == Activity.RESULT_OK) {
                startHomeActivity();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }

    private void startRegistrationPage() {
        final Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        startActivityForResult(intent, RC_REGISTRATION);
    }

    private void startHomeActivity() {
        final Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        DataManager.getInstance().getUserInformation(new ObjectResultListener() {

            @Override
            public void resultProcess(final Object result) {
                final UserModel userInformation = (UserModel) result;
                UserInfoProvider.saveUserId(getBaseContext(), userInformation.getUserId());
                UserInfoProvider.saveUserName(getBaseContext(), userInformation.getUserName(),
                        userInformation.getSurname());
                startActivity(intent);
                finish();
            }
        });
//        startActivity(intent);
//        finish();
    }
}
