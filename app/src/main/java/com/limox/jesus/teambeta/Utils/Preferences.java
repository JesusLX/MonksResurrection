package com.limox.jesus.teambeta.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.TeamBetaApplication;
import com.limox.jesus.teambeta.db.DatabaseManager;
import com.limox.jesus.teambeta.db.FirebaseContract;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jesús on 16/12/16.
 * This class is the manager of preferences of the application
 *
 * @author JesusLX
 */
public class Preferences {
    /**
     * Set in preferences who is the current user
     *
     * @param id       id user
     * @param email    email of the user
     * @param password Password of the user
     * @param context  Context of the application to access to sharedPreferences
     */
    public static void setCurrentUser(String id, String email, String password, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_ID))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_EMAIL);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_EMAIL))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_EMAIL);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_PSWRD))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_PSWRD);

        editor.putString(AllConstants.Keys.Shared.SHARED_USER_ID, id);
        editor.putString(AllConstants.Keys.Shared.SHARED_USER_EMAIL, email);
        editor.putString(AllConstants.Keys.Shared.SHARED_USER_PSWRD, password);

        editor.apply();
    }

    public static void removeCurrentUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_ID))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_EMAIL);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_EMAIL))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_EMAIL);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_PSWRD))
            editor.remove(AllConstants.Keys.Shared.SHARED_USER_PSWRD);

        editor.apply();
    }

    /**
     * Check if has a current user at the shared preferences
     *
     * @return
     */
    public static boolean loginCurrentUser(Context context, FirebaseContract.FirebaseUserCallback callback, Activity activity) {
        boolean hasUser = false;
        SharedPreferences preferences = TeamBetaApplication.getContext().getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE, MODE_PRIVATE);
        if (preferences.contains(AllConstants.Keys.Shared.SHARED_USER_EMAIL)) {
            if (Users_Repository.get().getCurrentUser() == null) {
                FirebaseContract.User.loginUser(preferences.getString(AllConstants.Keys.Shared.SHARED_USER_EMAIL, null), preferences.getString(AllConstants.Keys.Shared.SHARED_USER_PSWRD, null), context, activity, callback);
            }
            hasUser = true;
        }
        return hasUser;
    }

    public static String getSelectedForum() {
        SharedPreferences preferences = TeamBetaApplication.getContext().getSharedPreferences(AllConstants.Keys.Shared.SHARED_USER_FILE, MODE_PRIVATE);
        return preferences.getString(AllConstants.Keys.Shared.SHARED_FORUM_ID, null);
    }
}
