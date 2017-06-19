package com.limox.jesus.teambeta.Presenter;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.limox.jesus.teambeta.Interfaces.PostManagerPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.db.FirebaseContract;

/**
 * Presenter class to manage Posts
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
    public String uploadPost(Post post) {
        String key = FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                child(Users_Repository.get().getCurrentForum().getKey()).
                child(FirebaseContract.Posts.ROOT_NODE).
                push().getKey();

        FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                child(Users_Repository.get().getCurrentForum().getKey()).
                child(FirebaseContract.Posts.ROOT_NODE).
                child(key).setValue(post);
        return key;
    }


}
