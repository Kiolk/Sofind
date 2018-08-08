package com.github.kiolk.sofind.providers;

import android.content.Context;

/**
 * Class for management of manipulation with userInformation
 */
public final class UserInfoProvider {

    private static final String USER_ID = "UserId";
    private static final String USER_NAME = "UserName";

    public static void saveUserId(final Context context, final String userId){
        PrefSetter.putString(context, USER_ID, userId);
    }

    public static String getUserId(final Context context){
        return PrefGetter.getString(context, USER_ID);
    }

    public static void saveUserName(final Context context, final String userName, final String userSurname){
        PrefSetter.putString(context, USER_NAME, userName + " " + userSurname);
    }

    public static String getUserNameSurname(final Context context){
       return PrefGetter.getString(context, USER_NAME);
    }
}
