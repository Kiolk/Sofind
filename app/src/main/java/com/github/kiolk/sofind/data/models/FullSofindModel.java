package com.github.kiolk.sofind.data.models;

public class FullSofindModel extends SofindModel {

    private String mUserFullName;

    public FullSofindModel(){

    }

    public FullSofindModel(SofindModel sofind){
        setUserid(sofind.getUserid());
        setMindMessage(sofind.getMindMessage());
        setCreateTime(sofind.getCreateTime());
        setLikes(sofind.getLikes());
    }

    public String getUserFullName() {
        return mUserFullName;
    }

    public void setUserFullName(String mUserFullName) {
        this.mUserFullName = mUserFullName;
    }
}
