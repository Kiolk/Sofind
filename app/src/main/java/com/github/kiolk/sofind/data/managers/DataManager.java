package com.github.kiolk.sofind.data.managers;

import android.support.annotation.NonNull;

import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.activities.registration.RegistrationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DataManager implements RegistrationModel, RealDataBaseModel {

    private static DataManager mInstance;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    @Override
    public void userLogin(String email, String password, final SimpleResultListener listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.onSucces();
                } else {
                    listener.onError("Error");
                }
            }
        });
    }

    @Override
    public void registerNewUser(String email, String password, final SimpleResultListener simpleResultListener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    simpleResultListener.onSucces();
                } else {
                    simpleResultListener.onError("Error");
                }
            }
        });
    }

    @Override
    public void addStateListener(SimpleResultListener listener) {
        if (mAuthListener == null) {
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {

                    }
                }
            };
            FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void removeStateListener() {
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
            mAuthListener = null;
        }
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public String getUserUid() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return null;
    }

    @Override
    public void saveNewUser(UserModel user) {

    }

    @Override
    public void saveNewUser() {

    }

    @Override
    public List<UserModel> getAllUsers() {
        return null;
    }
}
