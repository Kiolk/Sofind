package com.github.kiolk.sofind.data.models;

import android.graphics.drawable.Drawable;

/**
 * Class model for storing simple information about setting item.
 */
public class SettingItemModel {

    private String mTitle;

    public SettingItemModel(final String title, final Drawable resource) {
        this.mTitle = title;
        this.mResource = resource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String mTitle) {
        this.mTitle = mTitle;
    }

    public Drawable getResource() {
        return mResource;
    }

    public void setResource(final Drawable mResource) {
        this.mResource = mResource;
    }

    private Drawable mResource;
}
