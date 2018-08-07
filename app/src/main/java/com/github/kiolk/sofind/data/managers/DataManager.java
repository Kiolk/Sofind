package com.github.kiolk.sofind.data.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.kiolk.sofind.data.ObjectResultListener;
import com.github.kiolk.sofind.data.SimpleResultListener;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.activities.registration.RegistrationModel;
import com.github.kiolk.sofind.ui.fragments.createsound.ISoundManager;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;
import com.github.kiolk.sofind.ui.fragments.yoursounds.YouSoundPresenter;
import com.github.kiolk.sofind.util.ConstantUtil;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager implements RegistrationModel, RealDataBaseModel, ISoundManager {

    private static final String SOFIND_USER = "SofindUsers";
    private static final String SOFIND_ITEMS = "SofindItems";

    private static DataManager mInstance;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserDatabaseReference;
    private DatabaseReference mSoundDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChildEventListener mSoundChildEventListener;
    private ChildEventListener mUpdateChildListener;

    private List<SofindModel> mSofindList = new ArrayList<>();
    private Map<String, String> mUsers = new HashMap<>();

    private int mLastSofinds = 8;

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
                        if (task.isSuccessful()) {
//                            task.getException().getMessage();
                            listener.onSuccess();
                        } else {
                            listener.onError("Error");
                        }

                    }
                });
    }

    @Override
    public void getUserInformation(final ObjectResultListener listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    mUsers.put(user.getUserId(), user.getUserName() + " " + user.getSurname());
                    if (user != null && user.getUserId().equals(userId)) {
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
    public void updateNewSound(FullSofindModel sofind, final SimpleResultListener listener) {
        mSoundDatabaseReference.child(sofind.getCreateTime() + "")
                .setValue(sofind).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onError("Error");
                }
            }
        });
    }

    @Override
    public void subscribeOnUsersSounds(final IYouSoundPresenter presenter) {
        final Query lastItems = mSoundDatabaseReference.orderByKey().limitToLast(ConstantUtil.FIRST_PORTION_OF_ITEMS);
        if (mSoundChildEventListener == null) {
            mSoundChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    presenter.updateYouSound(dataSnapshot.getValue(FullSofindModel.class));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    presenter.updateYouSound(dataSnapshot.getValue(SofindModel.class));
//                    lastItems.removeEventListener(mSoundChildEventListener);
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
        }
        lastItems.addChildEventListener(mSoundChildEventListener);
//        mSoundDatabaseReference.addChildEventListener(mSoundChildEventListener);
    }

    @Override
    public void unSubscribeOnUsersSounds() {
        if (mSoundChildEventListener != null) {
            mSoundDatabaseReference.removeEventListener(mSoundChildEventListener);
            mSoundChildEventListener = null;
        }
        if (mUpdateChildListener != null){
            mSoundDatabaseReference.removeEventListener(mUpdateChildListener);
            mUpdateChildListener = null;
        }
    }

    @Override
    public void getUserFullName(final String userId, final ObjectResultListener listener) {
        String fullName = mUsers.get(userId);
        if (fullName != null) {
            listener.resultProcess(fullName);
        } else {
            mUserDatabaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    mUsers.put(user.getUserId(), user.getUserName() + " " + user.getSurname());
                    if (user != null && user.getUserId().equals(userId)) {
                        String fullname = user.getUserName() + " " + user.getSurname();
                        mUserDatabaseReference.removeEventListener(this);
                        listener.resultProcess(fullname);
                    }
//                mUserDatabaseReference.removeEventListener(this);
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
            });
        }
    }

    @Override
    public void updateSound(final SofindModel updatedSofind) {
        final Query single = mSoundDatabaseReference;
        single.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FullSofindModel sofind = dataSnapshot.getValue(FullSofindModel.class);
                if (sofind.getCreateTime() == updatedSofind.getCreateTime()) {
                    int likes = sofind.getLikes();
                    updatedSofind.setLikes(likes + 1);
                    mSoundDatabaseReference.child(updatedSofind.getCreateTime() + "").setValue(updatedSofind);
                    single.removeEventListener(this);
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
        });
//        mSoundDatabaseReference.child(updatedSofind.getUserid()+updatedSofind.getCreateTime()).setValue(updatedSofind);
    }

    @Override
    public void loadMoreSofinds(final IYouSoundPresenter presenter, int getSofinds) {
//        mLastSofinds += 20;
        if(mUpdateChildListener == null){
            mUpdateChildListener = new ChildEventListener() {
                long lastUpdated = 0L;

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    FullSofindModel sofind = dataSnapshot.getValue(FullSofindModel.class);
                    if (lastUpdated == 0) {
                        lastUpdated = sofind.getCreateTime();
                    }
//                    if (lastUpdated >= sofind.getCreateTime()) {
                        presenter.updateAditionalItems(sofind);
//                    }

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
        }
        mSoundDatabaseReference.orderByKey().limitToLast(getSofinds).addChildEventListener(mUpdateChildListener);
//        mSoundDatabaseReference.orderByKey().limitToLast(getSofinds).addChildEventListener(new ChildEventListener() {
//            long lastUpdated = 0L;
//
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                FullSofindModel sofind = dataSnapshot.getValue(FullSofindModel.class);
//                if (lastUpdated == 0) {
//                    lastUpdated = sofind.getCreateTime();
//                }
//                if (lastUpdated >= sofind.getCreateTime()) {
//                    presenter.updateAditionalItems(sofind);
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
