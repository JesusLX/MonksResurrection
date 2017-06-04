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

    void getDescription(Forum forum);

    void uploadPhoto(Uri file, String folderName, String fileName);

    void existsForum(String forumsName, Response.Listener<String> valueEventListener);

    interface View {
        void onForumCreated(String forumKey);

        android.view.View getView();

        Context getContext();

        void onImageUploaded(Uri downloadUrl);

        void onImageFailed();

        void onError();

        void onDescriptionObtained(String description);
    }

}
