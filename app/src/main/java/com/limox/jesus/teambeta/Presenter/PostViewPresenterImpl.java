package com.limox.jesus.teambeta.Presenter;

import com.limox.jesus.teambeta.Interfaces.PostViewPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Repositories.Posts_Repository;

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
    public void changePostOfList(int idPost, @Post.STATE int newState) {
        this.mPostRep.changePostOfList(idPost,newState);
    }
}
