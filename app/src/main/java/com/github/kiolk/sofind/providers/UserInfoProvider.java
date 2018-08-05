package com.github.kiolk.sofind.providers;


import android.content.Context;

import com.github.kiolk.sofind.data.models.UserModel;

public class UserInfoProvider {

    private static final String USER_ID = "UserId";
    private static final String USER_NAME = "UserName";

    public static void saveUserId(Context context, String userId){
        PrefSetter.putString(context, USER_ID, userId);
    }

    public static String getUserId(Context context){
        return PrefGetter.getString(context, USER_ID);
    }

    public static void saveUserName(Context context, String userName, String userSurname){
        PrefSetter.putString(context, USER_NAME, userName + " " + userSurname);
    }

    public static String getUserNameSurname(Context context){
       return PrefGetter.getString(context, USER_NAME);
    }
}
