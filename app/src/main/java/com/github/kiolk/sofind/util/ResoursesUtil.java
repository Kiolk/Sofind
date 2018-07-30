package com.github.kiolk.sofind.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.github.kiolk.sofind.R;
import com.github.kiolk.sofind.data.models.SettingItemModel;

import java.util.ArrayList;
import java.util.List;

public class ResoursesUtil {

    public static ArrayList<SettingItemModel> getSettingsItem(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.SETTING_ARRAY);
        TypedArray iconArray = context.getResources().obtainTypedArray(R.array.SETTING_ICON_ARRAY);
        ArrayList<SettingItemModel> items = new ArrayList<>();
        for (int index = 0; index < stringArray.length; ++index) {
            items.add(new SettingItemModel(stringArray[index], iconArray.getDrawable(index)));
        }
        return items;
    }
}
