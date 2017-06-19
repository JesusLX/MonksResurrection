package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.limox.jesus.teambeta.Model.User;

/**
 * Created by jesus on 2/03/17.
 */

public interface UserManagerPresenter {
    void updateUser(String userPhoto, String userName, String userEmail, OnSuccessListener<Void> successListener);

    void getAllUsersOfForum(String forumId, String mListName);

    void addUser(User user);

    void getUser(String idUser);

    void getUser(String mUserName, String mPassword);

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
