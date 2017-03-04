package com.limox.jesus.teambeta_sqlite.Presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;

import com.limox.jesus.teambeta_sqlite.Interfaces.PostsListPresenter;
import com.limox.jesus.teambeta_sqlite.Model.Post;
import com.limox.jesus.teambeta_sqlite.Provider.TeamBetaContract;
import com.limox.jesus.teambeta_sqlite.Utils.AllConstants;
import com.limox.jesus.teambeta_sqlite.db.DatabaseContract;

/**
 * Created by jesus on 2/03/17.
 */

public class PostsListsPresenterImpl implements PostsListPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private PostsListPresenter.View view;

    public PostsListsPresenterImpl(PostsListPresenter.View view){
        this.view = view;
        context = view.getContext();
    }

    public void getAllPost(@Post.STATE int typeList){

        Loader<Cursor> loader = ((Activity)context).getLoaderManager().getLoader(1);
        if (loader == null)
            ((Activity)context).getLoaderManager().initLoader(typeList,null,this);
        else
            ((Activity) context).getLoaderManager().restartLoader(typeList, null, this);
    }
    public void getAllPost(@Post.STATE int typeList,int idUser){
        if (idUser != -1) {
            Bundle b = new Bundle();
            b.putInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY, idUser);
            Loader<Cursor> loader = ((Activity) context).getLoaderManager().getLoader(1);
            if (loader == null)
                ((Activity) context).getLoaderManager().initLoader(typeList, b, this);
            else
                ((Activity) context).getLoaderManager().restartLoader(typeList, b, this);
        }else
            getAllPost(typeList);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String where = null;
        String[] whereArgs = null;
        CursorLoader cursorLoader = null;
        switch (i){
            case Post.ALL:
                if (bundle != null){
                    where = TeamBetaContract.Posts.ID_USER+" = ? ";
                    whereArgs = new String[]{String.valueOf(bundle.getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY))};
                }
                cursorLoader = new CursorLoader(context, TeamBetaContract.Posts.CONTENT_URI,TeamBetaContract.Posts.PROJECTION_GET,where,whereArgs,null);
                break;
            case Post.NOT_PUBLISHED:

                if (bundle != null){
                    where = TeamBetaContract.Posts.ID_USER+" = ? AND "+TeamBetaContract.Posts.PUBLISHED + " = ? ";
                    whereArgs = new String[]{String.valueOf(bundle.getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY)),"0"};
                }else {
                    where = TeamBetaContract.Posts.PUBLISHED + " = ? ";
                    whereArgs = new String[]{"0"};
                }
                cursorLoader = new CursorLoader(context, TeamBetaContract.Posts.CONTENT_URI,TeamBetaContract.Posts.PROJECTION_GET,where,whereArgs, DatabaseContract.PostEntry.DESC_SORT);
                break;
            case Post.PUBLISHED:
                if (bundle != null){
                    where = TeamBetaContract.Posts.ID_USER+" = ? AND "+TeamBetaContract.Posts.PUBLISHED+" = ? AND "+TeamBetaContract.Posts.FIXED+" = ?";
                    whereArgs = new String[]{String.valueOf(bundle.getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY)),"1","0"};
                }else {
                    where = TeamBetaContract.Posts.PUBLISHED+" = ? AND "+TeamBetaContract.Posts.FIXED+" = ?";
                    whereArgs = new String[]{"1","0"};
                }

                cursorLoader = new CursorLoader(context, TeamBetaContract.Posts.CONTENT_URI,TeamBetaContract.Posts.PROJECTION_GET,where,whereArgs, DatabaseContract.PostEntry.DESC_SORT);
                break;
            case Post.FIXED:
                if (bundle != null){
                    where = TeamBetaContract.Posts.ID_USER+" = ? AND "+TeamBetaContract.Posts.FIXED+" = ?";
                    whereArgs = new String[]{String.valueOf(bundle.getInt(AllConstants.Keys.SimpleBundle.ID_USER_KEY)),"1"};
                }else {
                    where = TeamBetaContract.Posts.FIXED+" = ?";
                    whereArgs = new String[]{"1"};
                }

                cursorLoader = new CursorLoader(context, TeamBetaContract.Posts.CONTENT_URI,TeamBetaContract.Posts.PROJECTION_GET,where,whereArgs, DatabaseContract.PostEntry.ASC_SORT);
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        view.setCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        view.setCursor(null);

    }
}
