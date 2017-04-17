package com.limox.jesus.teambeta.Presenter;

import android.content.ContentValues;

import com.google.firebase.database.FirebaseDatabase;
import com.limox.jesus.teambeta.Interfaces.PostViewPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;
import com.limox.jesus.teambeta.Repositories.Posts_Repository;
import com.limox.jesus.teambeta.db.DatabaseContract;
import com.limox.jesus.teambeta.db.FirebaseContract;

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

        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Post.ROOT_NODE).child(String.valueOf(post.getIdPost())).child(FirebaseContract.Post.NODE_STATE).setValue(fixed + published);
/*
        where = TeamBetaContract.Posts._ID +" = ? AND "+ TeamBetaContract.Posts.FIXED + " = ? AND "+TeamBetaContract.Posts.PUBLISHED+" = ?";
        String[] whereArgs = new String[]{String.valueOf(post.getIdPost()), String.valueOf(fixed),String.valueOf(published)};*/

        post.setState(newState);

     /*   mView.getContext().getContentResolver().update(TeamBetaContract.Posts.CONTENT_URI,getContentPost(post),where,whereArgs);*/
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
