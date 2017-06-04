package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;

import com.limox.jesus.teambeta.Model.User;

/**
 * Created by jesus on 2/03/17.
 */

public interface UserManagerPresenter {
    void getAllUsersOfForum(String forumId, String mListName);

    void addUser(User user);

    void getUser(String idUser);

    void getUser(String mUserName, String mPassword);

    void aggregateForum(String forumKey, boolean admin, final ManagerView managerView);

    //void getUser(String idUser);

    public interface View {
        Context getContext();

        void showMessage(String message);

        void onUserCreated();

        void onUserObtained(User tryUser);
    }

    public interface ManagerView {
        Context getContext();

        void onForumAggregated(boolean admin);

        void onError();
    }
}
