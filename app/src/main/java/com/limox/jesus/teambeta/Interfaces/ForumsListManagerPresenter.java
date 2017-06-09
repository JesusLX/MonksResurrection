package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;

import com.limox.jesus.teambeta.Model.Forum;

import java.util.ArrayList;

/**
 * Created by Jesus on 27/04/2017.
 */

public interface ForumsListManagerPresenter {

    void searchForums(ArrayList<String> forumsKey);

    interface View {
        Context getContext();

        void onForumsObtained(ArrayList<Forum> forums);

        void addForum(Forum value);

        void onError();
    }
}
