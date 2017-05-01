package com.limox.jesus.teambeta.Presenter;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.limox.jesus.teambeta.Interfaces.PostManagerPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.db.FirebaseContract;

/**
 * Created by jesus on 3/03/17.
 */
public class PostManagerPresenterImpl implements PostManagerPresenter {

    private Context context;
    private PostManagerPresenter.View view;
    private static final int upload_post = 1;

    public PostManagerPresenterImpl(View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void uploadPost(Post post) {
        FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                child(Users_Repository.get().getCurrentForum().getKey()).
                child(FirebaseContract.Post.ROOT_NODE).
                child(Users_Repository.get().getCurrentForum().getKey()).
                push().setValue(post);
    }


}
