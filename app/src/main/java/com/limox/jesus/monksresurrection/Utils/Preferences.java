package com.limox.jesus.monksresurrection.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.limox.jesus.monksresurrection.Repositories.Users_Repository;

/**
 * Created by Jesús on 16/12/16.
 * This class is the manager of preferences of the application
 * @author JesusLX
 *
 */
public class Preferences {
    /**
     * Set in preferences who is the current user
     * @param name name of the user
     * @param passwrd Password of the user
     * @param context Context of the application to access to sharedPreferences
     */
    public static void setCurrentUser(String name, String passwrd, Context context){
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.SHARED_USER_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(AllConstants.SHARED_USER_NAME))
            editor.remove(AllConstants.SHARED_USER_NAME);
        if (preferences.contains(AllConstants.SHARED_USER_PSWRD))
            editor.remove(AllConstants.SHARED_USER_PSWRD);

        editor.putString(AllConstants.SHARED_USER_NAME,name);
        editor.putString(AllConstants.SHARED_USER_PSWRD,passwrd);

        editor.apply();
    }
    public static void removeCurrentUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.SHARED_USER_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(AllConstants.SHARED_USER_NAME))
            editor.remove(AllConstants.SHARED_USER_NAME);
        if (preferences.contains(AllConstants.SHARED_USER_PSWRD))
            editor.remove(AllConstants.SHARED_USER_PSWRD);

        editor.apply();
    }
    public static boolean hasACurrentUser(Context context){
        boolean hasUser = false;
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.SHARED_USER_FILE,Context.MODE_PRIVATE);
        if (preferences.contains(AllConstants.SHARED_USER_NAME)){
            if (Users_Repository.get().existUser(preferences.getString(AllConstants.SHARED_USER_NAME,null)))
                hasUser = true;
        }
        return hasUser;
    }
}
