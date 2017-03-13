package com.limox.jesus.teambeta.Presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.limox.jesus.teambeta.Interfaces.PostManagerPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;

/**
 * Created by jesus on 3/03/17.
 */

public class PostManagerPresenterImpl implements PostManagerPresenter,LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private PostManagerPresenter.View view;
    private static final int upload_post = 1;

    public PostManagerPresenterImpl(View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void uploadPost(Post post) {
        context.getContentResolver().insert(TeamBetaContract.Posts.CONTENT_URI,getContentPost(post));
    }

    private ContentValues getContentPost(Post post) {
        ContentValues values = new ContentValues();


        values.put(TeamBetaContract.Posts.TITLE, String.valueOf(post.getTitle()));
        values.put(TeamBetaContract.Posts.TEXT,String.valueOf(post.getDescription()));
        values.put(TeamBetaContract.Posts.ID_USER,post.getIdUser());
        values.put(TeamBetaContract.Posts.SCORE,post.getScore());
        values.put(TeamBetaContract.Posts.TAGS,String.valueOf(post.getTags()));
        values.put(TeamBetaContract.Posts.CREATION_DATE,post.getCreationDate().getTime());

        int fixed = 0;
        int published = 0;
        switch (post.getState()){
            case Post.FIXED:
                fixed = 1;
                published = 1;
                break;

            case Post.PUBLISHED:
                fixed = 0;
                published = 1;
                break;
        }

        values.put(TeamBetaContract.Posts.PUBLISHED,published);
        values.put(TeamBetaContract.Posts.FIXED,fixed);

        values.put(TeamBetaContract.Posts.DELETED,post.isDeleted() ? 1:0);
        return values;
    }

    private void startLoader(int selection,Bundle args){
        Loader<Cursor> loader = ((Activity)context).getLoaderManager().getLoader(2);

        if (loader == null){
            ((Activity)context).getLoaderManager().initLoader(selection,args,null);
        }else
            ((Activity)context).getLoaderManager().restartLoader(selection,args,null);


    }
}
