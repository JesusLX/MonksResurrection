package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Model.Forum;

/**
 * Created by Jesus on 27/04/2017.
 */

public interface ForumManagerPresenter {
    void createForum(Forum forum);

    void getForumFirebaseData(Forum forum);

    void uploadPhoto(Uri file, String folderName, String fileName);

    void existsForum(String forumsName, Response.Listener<String> valueEventListener);

    void updateForum(Forum mForum);

    interface View {
        void onForumCreated(String forumKey);

        android.view.View getView();

        Context getContext();

        void onImageUploaded(Uri downloadUrl);

        void onImageFailed();

        void onError();

        void onFirebaseForumObtained(Forum optForum);

    }

}
