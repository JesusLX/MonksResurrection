package com.limox.jesus.teambeta.Presenter;

import android.app.Activity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ForumsListManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.db.APIConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        } else {
            for (final String forumKey : forumsKey) {
                APIConstants.Forums.getForumByKey(mView.getContext(), forumKey, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            final Forum forum;
                            try {
                                forum = Forum.fromJSON(response.getJSONObject(i));

                                if (forum != null) {
                                    FirebaseDatabase.getInstance().getReference().
                                            child(FirebaseContract.Forums.ROOT_NODE).
                                            child(forumKey).
                                            child(FirebaseContract.Forums.NODE_DESCRIPTION).
                                            addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    forum.setDescription(dataSnapshot.getValue().toString());
                                                    mView.addForum(forum);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                }
                            } catch (JSONException e) {

                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        }
    }
}
