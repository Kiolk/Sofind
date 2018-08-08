package com.github.kiolk.sofind.data.models;

/**
 * Model for storing basic information about single sofind(Sound of mind) item.
 */
public class SofindModel {

    private String mUserId;
    private String mMindMessage;
    private long mCreateTime;
    private int mNumberOfLikes;

    private int mLikes;

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(final String mUserId) {
        this.mUserId = mUserId;
    }

    public String getMindMessage() {
        return mMindMessage;
    }

    public void setMindMessage(final String mMindMessage) {
        this.mMindMessage = mMindMessage;
    }

    public long getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(final long mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(final int mLikes) {
        this.mLikes = mLikes;
    }

    public int getNumberOfLikes() {
        return mNumberOfLikes;
    }

    public void setNumberOfLikes() {
        ++mNumberOfLikes;
    }
}
