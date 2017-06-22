package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Model.User;

/**
 * Created by jesus on 2/03/17.
 */

public interface UserManagerPresenter {

    void updateUser(String idUSer, Uri userPhoto, String userName, String userEmail, OnSuccessListener<Void> successListener, OnSuccessListener<UploadTask.TaskSnapshot> successPhotoListener);

    void getAllUsersOfForum(String forumId, String mListName);

    void addUser(User user);

    void getUser(String idUser);

    void getUser(String mUserName, String mPassword);

    void uploadPhoto(String iduser, Uri foto, OnSuccessListener<UploadTask.TaskSnapshot> successListener, OnFailureListener failureListener);

    void aggregateForum(String forumKey, boolean admin, final ManagerView managerView);

    void updateFirebaseEmail(String userKey, String email);

    void updateFirebaseName(String userKey, String name);


    //void getUser(String idUser);

    public interface View {
        Context getContext();

        void showMessage(String message);

        void onUserCreated();

        void onUserObtained(User tryUser);

        void onError(Exception exception);


    }

    public interface ManagerView {
        Context getContext();

        void onForumAggregated(boolean admin);

        void onError();
    }
}
