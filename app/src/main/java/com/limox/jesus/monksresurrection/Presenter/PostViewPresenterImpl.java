package com.limox.jesus.monksresurrection.Presenter;

import com.limox.jesus.monksresurrection.Interfaces.PostViewPresenter;
import com.limox.jesus.monksresurrection.Model.Post;
import com.limox.jesus.monksresurrection.Repositories.Posts_Repository;

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
