package com.limox.jesus.teambeta.Presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.PostsListPresenter;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Provider.TeamBetaContract;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.db.DatabaseContract;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;

/**
 * Created by jesus on 2/03/17.
 */

public class PostsListsPresenterImpl implements PostsListPresenter {

    private Context context;
    private PostsListPresenter.View view;

    public PostsListsPresenterImpl(PostsListPresenter.View view) {
        this.view = view;
        context = view.getContext();
    }

    public void getAllPost(@Post.STATE int typeList) {

        FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                child(Users_Repository.get().getCurrentForum().getKey()).
                child(FirebaseContract.Posts.ROOT_NODE).
                orderByChild(FirebaseContract.Posts.NODE_STATE).equalTo(typeList).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Post> posts = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Post tmpPost = child.getValue(Post.class);
                    tmpPost.setIdPost(child.getKey());
                    posts.add(tmpPost);
                }
                view.setData(posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getAllPost(@Post.STATE int typeList, String idUser) {
        if (!idUser.equals("")) {
            FirebaseDatabase.getInstance().getReference().
                    child(FirebaseContract.Forums.ROOT_NODE).
                    child(Users_Repository.get().getCurrentForum().getKey()).
                    child(FirebaseContract.Posts.ROOT_NODE).
                    orderByChild(FirebaseContract.Posts.NODE_OWNER).equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Post> posts = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Post tmpPost = child.getValue(Post.class);
                        tmpPost.setIdPost(child.getKey());
                        posts.add(tmpPost);
                    }
                    view.setData(posts);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else
            getAllPost(typeList);
    }
}
