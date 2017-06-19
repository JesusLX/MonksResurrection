package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.net.Uri;

import com.limox.jesus.teambeta.Model.Commentary;
import com.limox.jesus.teambeta.Model.Forum;

import java.util.ArrayList;

/**
 * Created by Jesus on 15/06/2017.
 */

public interface CommentaryManagerPresenter {
    void getComments(String forumKey, String postKey);

    void sendComment(String forumKey, String postKey, Commentary commentary);

    interface View {
        void onCommentreated(String forumKey);

        android.view.View getView();

        Context getContext();

        void onImageUploaded(Uri downloadUrl);

        void onImageFailed();

        void onError();

        void onCommentsObtained(ArrayList<Commentary> comments);
    }
}
