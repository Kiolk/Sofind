package com.github.kiolk.sofind.data.models;


import android.graphics.drawable.Drawable;

public class SettingItemModel {

    private String mTitle;

    public SettingItemModel(String title, Drawable resource) {
        this.mTitle = title;
        this.mResourse = resource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Drawable getResourse() {
        return mResourse;
    }

    public void setResourse(Drawable mResourse) {
        this.mResourse = mResourse;
    }

    private Drawable mResourse;
}
