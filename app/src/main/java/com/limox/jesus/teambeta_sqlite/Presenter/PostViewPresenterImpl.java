package com.limox.jesus.teambeta_sqlite.Presenter;

import android.content.ContentValues;
import android.os.StrictMode;
import android.util.StringBuilderPrinter;

import com.limox.jesus.teambeta_sqlite.Interfaces.PostViewPresenter;
import com.limox.jesus.teambeta_sqlite.Model.Post;
import com.limox.jesus.teambeta_sqlite.Provider.TeamBetaContract;
import com.limox.jesus.teambeta_sqlite.Repositories.Posts_Repository;

/**
 * Created by jesus on 30/12/16.
 */

public class PostViewPresenterImpl implements PostViewPresenter{

    private PostViewPresenter.View mView;
    private Posts_Repository mPostRep;


    public PostViewPresenterImpl(View mView) {
        this.mView = mView;
        this.mPostRep = Posts_Repository.get();
    }

    @Override
    public void deletePost(Post mPost) {
        String where = TeamBetaContract.Posts._ID +"= ?";
        String[] whereArgs = new String[] {String.valueOf( mPost.getIdPost())};
        mView.getContext().getContentResolver().delete(TeamBetaContract.Posts.CONTENT_URI,where,whereArgs);
    }

    @Override
    public void changePostOfList(Post post, @Post.STATE int newState) {
        String where;
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
        where = TeamBetaContract.Posts._ID +" = ? AND "+ TeamBetaContract.Posts.FIXED + " = ? AND "+TeamBetaContract.Posts.PUBLISHED+" = ?";
        String[] whereArgs = new String[]{String.valueOf(post.getIdPost()), String.valueOf(fixed),String.valueOf(published)};

        post.setState(newState);

        mView.getContext().getContentResolver().update(TeamBetaContract.Posts.CONTENT_URI,getContentPost(post),where,whereArgs);
    }
    private ContentValues getContentPost(Post post) {
        ContentValues values = new ContentValues();


        values.put(TeamBetaContract.Posts.TITLE,post.getTitle());
        values.put(TeamBetaContract.Posts.TEXT,post.getDescription());
        values.put(TeamBetaContract.Posts.ID_USER,post.getIdUser());
        values.put(TeamBetaContract.Posts.SCORE,post.getScore());
        values.put(TeamBetaContract.Posts.TAGS,post.getTags());
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

}
