package com.limox.jesus.teambeta.Presenter;

import android.app.Activity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ForumsListManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.ArrayList;

/**
 * Created by Jesus on 27/04/2017.
 */

public class ForumsListManagerPresenterImpl implements ForumsListManagerPresenter {
    ForumsListManagerPresenter.View mView;
    ValueEventListener vel;

    public ForumsListManagerPresenterImpl(final View mView) {
        this.mView = mView;

    }

    @Override
    public void searchForums(ArrayList<String> forumsKey) {
        if (forumsKey == null || forumsKey.size() == 0) {
            mView.addForum(null);
        } else
            vel = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Forum tmp = dataSnapshot.getValue(Forum.class);
                    tmp.setKey(dataSnapshot.getKey());
                    mView.addForum(tmp);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            for (String forumKey : forumsKey) {
                FirebaseDatabase.getInstance().getReference().
                        child(FirebaseContract.Forums.ROOT_NODE).
                        child(forumKey).addListenerForSingleValueEvent(vel);

            }
    }
}
