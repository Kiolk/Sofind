package com.github.kiolk.sofind.data.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.activities.registration.RegistrationModel;
import com.github.kiolk.sofind.ui.fragments.createsound.ISoundManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DataManager implements RegistrationModel, RealDataBaseModel, ISoundManager {

    private static final String SOFIND_USER =  "SofindUsers";
    private static final String SOFIND_ITEMS =  "SofindItems";

    private static DataManager mInstance;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mSoundDatabaseReference;
    private ChildEventListener mChildEventListener;

    private DataManager() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserDatabaseReference = mFirebaseDatabase.getReference().child(SOFIND_USER);
        mSoundDatabaseReference = mFirebaseDatabase.getReference().child(SOFIND_ITEMS);
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
                    listener.onSuccess();
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
                    simpleResultListener.onSuccess();
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
    public void saveNewUser(UserModel user, final SimpleResultListener listener) {
        mUserDatabaseReference.child(user.getUserId()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
//                            task.getException().getMessage();
                            listener.onSuccess();
                        }else{
                            listener.onError("Error");
                        }

                    }
                });
    }

    @Override
    public void getUserInformation(final ObjectResultListener listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    if(user != null && user.getUserId().equals(userId)){
                        listener.resultProcess(user);
                        mUserDatabaseReference.removeEventListener(mChildEventListener);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mUserDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    public List<UserModel> getAllUsers() {
        return null;
    }

    @Override
    public void updateNewSound(SofindModel sofind, final SimpleResultListener listener) {
        mSoundDatabaseReference.child(sofind.getUserid() + sofind.getCreateTime())
                .setValue(sofind).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    listener.onSuccess();
                }else{
                    listener.onError("Error");
                }
            }
        });
    }
}
