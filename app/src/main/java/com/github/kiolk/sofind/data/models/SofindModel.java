package com.github.kiolk.sofind.data.models;

public class SofindModel {

    private String mUserid;
    private String mMindMessage;
    private long mCreateTime;
    private int mNumberOfLikes;

    private int mLikes;

    public String getUserid() {
        return mUserid;
    }

    public void setUserid(String mUserid) {
        this.mUserid = mUserid;
    }

    public String getMindMessage() {
        return mMindMessage;
    }

    public void setMindMessage(String mMindMessage) {
        this.mMindMessage = mMindMessage;
    }

    public long getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(long mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int mLikes) {
        this.mLikes = mLikes;
    }

    public int getNumberOfLikes() {
        return mNumberOfLikes;
    }

    public void setNumberOfLikes(){
        ++mNumberOfLikes;
    }
}
