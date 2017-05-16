package com.limox.jesus.teambeta.Presenter;

import android.content.ContentValues;

import com.limox.jesus.teambeta.Interfaces.PostViewPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;
import com.limox.jesus.teambeta.Repositories.PostsStorage;
import com.limox.jesus.teambeta.db.FirebaseContract;

/**
 * Created by jesus on 30/12/16.
 */

public class PostViewPresenterImpl implements PostViewPresenter{

    private PostViewPresenter.View mView;
    private PostsStorage mPostRep;


    public PostViewPresenterImpl(View mView) {
        this.mView = mView;
        this.mPostRep = PostsStorage.get();
    }

    @Override
    public void deletePost(String idPost) {
        FirebaseContract.Posts.deletePost(idPost);

    }

    @Override
    public void likePost(String idPost, Post post) {
        FirebaseContract.Posts.likePost(idPost, post);
    }

    @Override
    public void changePostOfList(Post post, @Post.STATE int newState) {

        FirebaseContract.Posts.changePostsList(post.getIdPost(), newState);
        post.setState(newState);
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
