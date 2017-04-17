package com.limox.jesus.teambeta.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;

/**
 * Created by usuario on 13/03/17.
 */

public class DatabaseManager {

    public static boolean existsUser(String userName){
        boolean exists = false;
        String where = DatabaseContract.UserEntry.COLUMN_NAME+" = ?";
        String[] whereArg = new String[] {userName};
        SQLiteDatabase db = DatabaseHelper.getInstance().openDatabase();

        Cursor cursor = db.query(DatabaseContract.UserEntry.TABLE_NAME,new String[]{DatabaseContract.UserEntry.COLUMN_NAME},where,whereArg,null,null,null);
        exists = cursor.moveToFirst();
        DatabaseHelper.getInstance().closeDatabase();

        return exists;
    }
    public static User getUser(String userName){
        User user = null;
        String where = DatabaseContract.UserEntry.COLUMN_NAME+" = ?";
        String[] whereArg = new String[] {userName};
        SQLiteDatabase db = DatabaseHelper.getInstance().openDatabase();

        Cursor cursor = db.query(DatabaseContract.UserEntry.TABLE_NAME, TeamBetaContract.Users.PROJECTION_GET,where,whereArg,null,null,null);
        if (cursor.moveToFirst()){
            user = new User();
            user.setIdUser(cursor.getString(cursor.getColumnIndex(TeamBetaContract.Users._ID)));
            user.setBlocked(cursor.getInt(cursor.getColumnIndex(TeamBetaContract.Users.BLOCKED))==1);
            user.setDeleted(cursor.getInt(cursor.getColumnIndex(TeamBetaContract.Users.DELETED))==1);
            user.setEmail(cursor.getString(cursor.getColumnIndex(TeamBetaContract.Users.EMAIL)));
            user.setProfilePicture(cursor.getString(cursor.getColumnIndex(TeamBetaContract.Users.ICON)));
            user.setName(cursor.getString(cursor.getColumnIndex(TeamBetaContract.Users.NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(TeamBetaContract.Users.PASSWORD)));
            //        user.setPostsLiked(cursor.getString(cursor.getColumnIndex(TeamBetaContract.Users.POSTS_LIKED)));
            //  user.setUserType(cursor.getInt(cursor.getColumnIndex(TeamBetaContract.Users.TYPE_USER)));
        }
        DatabaseHelper.getInstance().closeDatabase();

        return user;
    }

}
