package com.github.kiolk.sofind.data.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Model for storing information about user.
 */
public class UserModel implements Serializable {

    private String mUserId;
    private String mUserName;
    private String mSurname;
    private String mEmail;
    private String mPassword;
    private int mAge;
    private boolean isMale;

    public void setUserId(final String value) {
        mUserId = value;
    }

    @NonNull
    public String getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(final String mUserName) {
        this.mUserName = mUserName;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(final String mSurname) {
        this.mSurname = mSurname;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(final String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(final String mPassword) {
        this.mPassword = mPassword;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(final int mAge) {
        this.mAge = mAge;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(final boolean male) {
        isMale = male;
    }
}
