package com.github.kiolk.sofind.data.managers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.kiolk.sofind.data.listeners.ObjectResultListener;
import com.github.kiolk.sofind.data.listeners.SimpleResultListener;
import com.github.kiolk.sofind.data.models.FullSofindModel;
import com.github.kiolk.sofind.data.models.SofindModel;
import com.github.kiolk.sofind.data.models.UserModel;
import com.github.kiolk.sofind.ui.activities.registration.RegistrationModel;
import com.github.kiolk.sofind.ui.fragments.createsound.ISoundManager;
import com.github.kiolk.sofind.ui.fragments.yoursounds.IYouSoundPresenter;
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

import java.util.HashMap;
import java.util.Map;

public final class DataManager implements RegistrationModel, RealDataBaseModel, ISoundManager {

    private static final String SOFIND_USER = "SofindUsers";
    private static final String SOFIND_ITEMS = "SofindItems";

    private static DataManager mInstance;

    private final DatabaseReference mUserDatabaseReference;
    private final DatabaseReference mSoundDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChildEventListener mSoundChildEventListener;
    private ChildEventListener mUpdateChildListener;

    private final Map<String, String> mUsers = new HashMap<>();

    private DataManager() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mUserDatabaseReference = firebaseDatabase.getReference().child(SOFIND_USER);
        mSoundDatabaseReference = firebaseDatabase.getReference().child(SOFIND_ITEMS);
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    @Override
    public void userLogin(final String email, final String password, final SimpleResultListener listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onError();
                }
            }
        });
    }

    @Override
    public void registerNewUser(final String email, final String password, final SimpleResultListener simpleResultListener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    simpleResultListener.onSuccess();
                } else {
                    simpleResultListener.onError();
                }
            }
        });
    }

    @Override
    public void signOut(final SimpleResultListener simpleResultListener) {
        FirebaseAuth.getInstance().signOut();
        simpleResultListener.onSuccess();
    }

    @Override
    public String getUserUid() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return null;
    }

    @Override
    public void saveNewUser(final UserModel user, final SimpleResultListener listener) {
        mUserDatabaseReference.child(user.getUserId()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onError();
                        }

                    }
                });
    }

    @Override
    public void getUserInformation(final ObjectResultListener listener) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mChildEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                    final UserModel user = dataSnapshot.getValue(UserModel.class);
                    if (user != null) {
                        mUsers.put(user.getUserId(), user.getUserName() + " " + user.getSurname());

                        if (user.getUserId().equals(userId)) {
                            listener.resultProcess(user);
                            mUserDatabaseReference.removeEventListener(mChildEventListener);
                        }
                    }

                }

                @Override
                public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onCancelled(@NonNull final DatabaseError databaseError) {

                }
            };
            mUserDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    @Override
    public void updateNewSound(final FullSofindModel sofind, final SimpleResultListener listener) {
        mSoundDatabaseReference.child(sofind.getCreateTime() + "")
                .setValue(sofind).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull final Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onError();
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
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                    presenter.updateYouSound(dataSnapshot.getValue(FullSofindModel.class));
                }

                @Override
                public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                }

                @Override
                public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onCancelled(@NonNull final DatabaseError databaseError) {

                }
            };
        }
        lastItems.addChildEventListener(mSoundChildEventListener);
    }

    @Override
    public void unSubscribeOnUsersSounds() {
        if (mSoundChildEventListener != null) {
            mSoundDatabaseReference.removeEventListener(mSoundChildEventListener);
            mSoundChildEventListener = null;
        }
        if (mUpdateChildListener != null) {
            mSoundDatabaseReference.removeEventListener(mUpdateChildListener);
            mUpdateChildListener = null;
        }
    }

    @Override
    public void getUserFullName(final String userId, final ObjectResultListener listener) {
        final String fullName = mUsers.get(userId);
        if (fullName != null) {
            listener.resultProcess(fullName);
        } else {
            mUserDatabaseReference.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                    final UserModel user = dataSnapshot.getValue(UserModel.class);
                    mUsers.put(user.getUserId(), user.getUserName() + " " + user.getSurname());
                    if (user.getUserId().equals(userId)) {
                        final String fullName = user.getUserName() + " " + user.getSurname();
                        mUserDatabaseReference.removeEventListener(this);
                        listener.resultProcess(fullName);
                    }
                }

                @Override
                public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onCancelled(@NonNull final DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void updateSound(final SofindModel updatedSofind) {
        final Query single = mSoundDatabaseReference;
        single.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                final FullSofindModel sofind = dataSnapshot.getValue(FullSofindModel.class);
                if (sofind.getCreateTime() == updatedSofind.getCreateTime()) {
                    final int likes = sofind.getLikes();
                    updatedSofind.setLikes(likes + 1);
                    mSoundDatabaseReference.child(String.valueOf(updatedSofind.getCreateTime())).setValue(updatedSofind);
                    single.removeEventListener(this);
                }
            }

            @Override
            public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

            }

            @Override
            public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void loadMoreSofinds(final IYouSoundPresenter presenter, final int getSofinds) {
        if (mUpdateChildListener == null) {
            mUpdateChildListener = new ChildEventListener() {

                long lastUpdated;

                @Override
                public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {
                    final FullSofindModel sofind = dataSnapshot.getValue(FullSofindModel.class);
                    if (lastUpdated == 0) {
                        lastUpdated = sofind.getCreateTime();
                    }
//                    if (lastUpdated >= sofind.getCreateTime()) {
                    presenter.updateAdditionalItems(sofind);
//                    }

                }

                @Override
                public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, @Nullable final String s) {

                }

                @Override
                public void onCancelled(@NonNull final DatabaseError databaseError) {

                }
            };
        }
        mSoundDatabaseReference.orderByKey().limitToLast(getSofinds).addChildEventListener(mUpdateChildListener);
    }

    public void changeUserFullName(final String userId, final String fulUserName) {
        mUsers.put(userId, fulUserName);
    }
}
