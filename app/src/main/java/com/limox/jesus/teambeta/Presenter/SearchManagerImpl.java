package com.limox.jesus.teambeta.Presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.SearchManager;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.db.APIConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jesus on 16/05/2017.
 */

public class SearchManagerImpl implements SearchManager {

    private View mView;

    public SearchManagerImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void searchCoincidences(final String name) {

        APIConstants.Forums.getNames(mView.getContext(), name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String> names = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject name = jsonArray.getJSONObject(i);
                        names.add(name.optString(APIConstants.Forums.FORUM_NAME));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mView.onNamesFound(names);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.onError();
            }
        });
    }

    @Override
    public void searchForums(String name) {
        APIConstants.Forums.getForum(mView.getContext(), name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ArrayList<Forum> forums = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject forum = jsonArray.getJSONObject(i);
                        final Forum tmp = Forum.fromJSON(forum.toString());
                        FirebaseDatabase.getInstance().getReference().
                                child(FirebaseContract.Forums.ROOT_NODE).
                                child(tmp.getKey()).
                                child(FirebaseContract.Forums.NODE_DESCRIPTION).
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        tmp.setDescription(dataSnapshot.getValue().toString());
                                        forums.add(tmp);
                                        if (mView != null)
                                            mView.onForumsFound(forums);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.onError();
            }
        });
    }
}
