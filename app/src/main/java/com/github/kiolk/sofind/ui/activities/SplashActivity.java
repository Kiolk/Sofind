package com.github.kiolk.sofind.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.ui.activities.base.BaseActivity;
import com.github.kiolk.sofind.ui.activities.home.HomeActivity;
import com.github.kiolk.sofind.ui.activities.registration.RegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseActivity {

    private static final int RC_REGISTRATION = 0;

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkAuthentication();
    }

    private void checkAuthentication(){
        mFirebaseAuthListener = new  FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    startHomeActivity();
                }else{
                    startRegistrationPage();
                }
            }
        };
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_REGISTRATION){
            if(resultCode == Activity.RESULT_OK){
                startHomeActivity();
            }else if(resultCode == Activity.RESULT_CANCELED){
                finish();
            }
        }
    }

    private void startRegistrationPage() {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        startActivityForResult(intent, RC_REGISTRATION);
    }

    private void startHomeActivity() {
//        Toast.makeText(getBaseContext(), getBaseContext().getResources()
//                .getString(R.string.SUCCES_LOGIN), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        startActivity(intent);
    }
}
