package com.limox.jesus.teambeta_sqlite.Presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;

import com.limox.jesus.teambeta_sqlite.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta_sqlite.Model.User;
import com.limox.jesus.teambeta_sqlite.Provider.TeamBetaContract;
import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.Utils.AllConstants;

/**
 * Created by jesus on 2/03/17.
 */

public class UserManagerPresenterImpl implements UserManagerPresenter,LoaderManager.LoaderCallbacks<Cursor> {

    private UserManagerPresenter.View view;
    private Context context;
    private User tryUser;

    private static final int getUserByName = 1;
    private static final int getUserByNamAndPass = 2;
    private static final int getUserById = 3;
    private int selection;

    public UserManagerPresenterImpl(View view) {
        this.view = view;
        this.context = view.getContext();
    }

    public void getUser(String user){

        Bundle usr = new Bundle();
        usr.putString(AllConstants.Keys.Parcelables.USER_NAME_PARCELABLE_KEY,user);
        selection = getUserByName;
        loadCursor(selection,usr);
    }
    private void loadCursor(int selection, Bundle bundle){
        Loader<Cursor> loader = ((Activity)context).getLoaderManager().getLoader(0);
        if(loader == null){
            ((Activity)context).getLoaderManager().initLoader(selection,bundle,this);
        }else
            ((Activity)context).getLoaderManager().restartLoader(selection,bundle,this);

    }
    public void getUser(String user, String password){
        Bundle usr = new Bundle();
        usr.putString(AllConstants.Keys.Parcelables.USER_NAME_PARCELABLE_KEY,user);
        usr.putString(AllConstants.Keys.Parcelables.USER_PASSWORD_PARCELABLE_KEY,password);
        selection = getUserByNamAndPass;
        loadCursor(selection,usr);
    }

    @Override
    public void getUser(int idUser) {
        selection = getUserById;
        Bundle b = new Bundle();
        b.putInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY,idUser);
        loadCursor(selection,b);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String where;
        String[] whereArgs;
        CursorLoader cursorLoader = null;
        switch (i){
            case getUserByName:
                where= TeamBetaContract.Users.NAME+" = ?";
                whereArgs = new String[]{bundle.getString(AllConstants.Keys.Parcelables.USER_NAME_PARCELABLE_KEY)};
                cursorLoader = new CursorLoader(context,TeamBetaContract.Users.CONTENT_URI,TeamBetaContract.Users.PROJECTION_SET,where,whereArgs,null);
                break;
            case getUserByNamAndPass:
                where= TeamBetaContract.Users.NAME+" = ? AND "+TeamBetaContract.Users.PASSWORD+" = ?";
                whereArgs = new String[]{bundle.getString(AllConstants.Keys.Parcelables.USER_NAME_PARCELABLE_KEY),bundle.getString(AllConstants.Keys.Parcelables.USER_PASSWORD_PARCELABLE_KEY)};
                cursorLoader = new CursorLoader(context,TeamBetaContract.Users.CONTENT_URI,TeamBetaContract.Users.PROJECTION_GET,where,whereArgs,null);
                break;

            case getUserById:
                where= TeamBetaContract.Users._ID+" = ? ";
                whereArgs = new String[]{String.valueOf(bundle.getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY))};
                cursorLoader = new CursorLoader(context,TeamBetaContract.Users.CONTENT_URI,TeamBetaContract.Users.PROJECTION_GET,where,whereArgs,null);
                break;

        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch (selection){
            case getUserByName:
                if (cursor.getCount()<= 0){
                    try {
                        ContentValues contentValues;
                        contentValues = getContentUser(tryUser);
                        context.getContentResolver().insert(TeamBetaContract.Users.CONTENT_URI, contentValues);
                        view.onUserCreated();
                        view.onUserObtained(tryUser);
                    } catch (SQLException e) {
                        view.showMessage(e.getMessage());
                    }
                }else{
                    view.showMessage(context.getResources().getString(R.string.user_exists));
                }
                break;
            case getUserByNamAndPass:
                if (cursor.getCount()>0){
                    setUser(cursor);
                    view.onUserObtained(tryUser);
                }else
                    view.showMessage(context.getResources().getString(R.string.user_error));
                break;
            case getUserById:
                if (cursor.getCount()>0){
                    setUser(cursor);
                    view.onUserObtained(tryUser);
                }
                break;
        }
        ((Activity)context).getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    @Override
    public void addUser(User user) {

        //Encriptar la contraseña
        tryUser = user;

        getUser(user.getName());


    }

    private ContentValues getContentUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TeamBetaContract.Users.NAME, user.getName());
        contentValues.put(TeamBetaContract.Users.PASSWORD, user.getPassword());
        contentValues.put(TeamBetaContract.Users.EMAIL, user.getEmail());
        contentValues.put(TeamBetaContract.Users.ICON, user.getIcon());
        contentValues.put(TeamBetaContract.Users.POSTS_LIKED, user.getPostsLiked_URL());
        contentValues.put(TeamBetaContract.Users.BLOCKED, user.isBlocked());
        contentValues.put(TeamBetaContract.Users.DELETED, user.isDeleted());
        contentValues.put(TeamBetaContract.Users.TYPE_USER, user.getTypeUser());

        return contentValues;
    }

    public void setUser(Cursor cursor) {
        cursor.moveToFirst();
        tryUser = new User();
        tryUser.setIdUser(cursor.getInt(TeamBetaContract.Users.ID_KEY));
        tryUser.setName(cursor.getString(TeamBetaContract.Users.NAME_KEY));
        tryUser.setPassword(cursor.getString(TeamBetaContract.Users.PASSWORD_KEY));
        tryUser.setEmail(cursor.getString(TeamBetaContract.Users.EMAIL_KEY));
        tryUser.setIcon(cursor.getString(TeamBetaContract.Users.ICON_KEY));
        tryUser.setPostsLiked_URL(cursor.getString(TeamBetaContract.Users.POSTS_LIKED_KEY));
        tryUser.setBlocked(cursor.getInt(TeamBetaContract.Users.BLOCKED_KEY)==1);
        tryUser.setDeleted(cursor.getInt(TeamBetaContract.Users.DELETED_KEY) == 1);
        tryUser.setUserType(cursor.getInt(TeamBetaContract.Users.TYPE_USER_KEY));
    }
}
