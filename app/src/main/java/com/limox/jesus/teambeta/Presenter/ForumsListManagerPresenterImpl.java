package com.limox.jesus.teambeta.Presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ForumsListManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.APIConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Presenter class to manage Forum's lists
 * Created by Jesus on 27/04/2017.
 */
public class ForumsListManagerPresenterImpl implements ForumsListManagerPresenter {
    private ForumsListManagerPresenter.View mView;

    public ForumsListManagerPresenterImpl(final View mView) {
        this.mView = mView;
    }

    @Override
    public void searchForums(ArrayList<String> forumsKey) {
        if (mView != null)
            if (UIUtils.isNetworkAvailable(mView.getContext())) {
                if (forumsKey == null || forumsKey.size() == 0) {
                    if (mView != null)
                        mView.addForum(null);
                } else {
                    for (final String forumKey : forumsKey) {
                        if (mView != null)
                            APIConstants.Forums.getForumByKey(mView.getContext(), forumKey, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    for (int i = 0; i < response.length(); i++) {
                                        final Forum forum;
                                        try {
                                            forum = Forum.fromJSON(response.getJSONObject(i));
                                            forum.setKey(forumKey);
                                            if (mView != null)
                                                mView.addForum(forum);

                                        } catch (JSONException e) {

                                        }
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (mView != null)
                                        mView.onError();
                                }
                            });
                    }
                }
            } else {
                if (mView != null) {
                    mView.onError();
                    UIUtils.toast(mView.getContext(), mView.getContext().getString(R.string.connection_error));
                }
            }
    }
}
