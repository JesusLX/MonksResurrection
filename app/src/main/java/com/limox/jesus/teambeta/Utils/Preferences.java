package com.limox.jesus.teambeta.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.TeamBetaApplication;
import com.limox.jesus.teambeta.db.DatabaseManager;

import static android.content.Context.MODE_PRIVATE;

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
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_NAME))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_NAME);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_PSWRD))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_PSWRD);

        editor.putString(AllConstants.Keys.Shared.SHARED_USER_NAME,name);
        editor.putString(AllConstants.Keys.Shared.SHARED_USER_PSWRD,passwrd);

        editor.apply();
    }
    public static void removeCurrentUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_NAME))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_NAME);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_PSWRD))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_PSWRD);

        editor.apply();
    }

    /**
     * Check if has a current user at the shared preferences
     * @return
     */
    public static boolean hasACurrentUser(){
        boolean hasUser = false;
        SharedPreferences preferences = TeamBetaApplication.getContext().getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE, MODE_PRIVATE);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_NAME)){
            if (DatabaseManager.existsUser(preferences.getString(AllConstants.Keys.Shared.SHARED_USER_NAME,null))) {
                hasUser = true;
                if (Users_Repository.get().getCurrentUser() == null)
                    Users_Repository.get().setCurrentUser(DatabaseManager.getUser(preferences.getString(AllConstants.Keys.Shared.SHARED_USER_NAME,null)));
            }
        }
        return hasUser;
    }
}