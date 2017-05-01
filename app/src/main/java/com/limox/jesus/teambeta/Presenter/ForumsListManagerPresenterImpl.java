package com.limox.jesus.teambeta.Presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ForumsListManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;

/**
 * Created by Jesus on 27/04/2017.
 */

public class ForumsListManagerPresenterImpl implements ForumsListManagerPresenter {
    ForumsListManagerPresenter.View mView;

    public ForumsListManagerPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void searchForums(ArrayList<String> forumsKey) {
        if (forumsKey == null || forumsKey.size() == 0) {
            mView.addForum(null);
        } else
            for (String forumKey : forumsKey) {
                FirebaseDatabase.getInstance().getReference().
                        child(FirebaseContract.Post.ROOT_NODE).
                        child(forumKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mView.addForum(dataSnapshot.getValue(Forum.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
    }
}
