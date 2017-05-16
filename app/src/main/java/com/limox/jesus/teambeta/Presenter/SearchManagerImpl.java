package com.limox.jesus.teambeta.Presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.SearchManager;
import com.limox.jesus.teambeta.db.FirebaseContract;

/**
 * Created by Jesus on 16/05/2017.
 */

public class SearchManagerImpl implements SearchManager {

    private View mView;

    public SearchManagerImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void searchCoincidences(String tag) {
        FirebaseContract.Forums.getTags(tag, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void searchForums(String tag) {
        FirebaseContract.Forums.getForums(tag, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
