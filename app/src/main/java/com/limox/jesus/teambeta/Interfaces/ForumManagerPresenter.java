package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Model.Forum;

/**
 * Created by Jesus on 27/04/2017.
 */

public interface ForumManagerPresenter {
    void createForum(Forum forum);

    void uploadPhoto(Uri file, String folderName, String fileName);

    void existsForum(String forumsName, ValueEventListener valueEventListener);

    interface View {
        void onForumCreated();

        android.view.View getView();

        Context getContext();

        void onImageUploaded(Uri downloadUrl);

        void onImageFailed();
    }
}
