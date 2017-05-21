package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.view.View;

import com.limox.jesus.teambeta.Model.Forum;

import java.util.ArrayList;

/**
 * Created by Jesus on 16/05/2017.
 */

public interface SearchManager {
    void searchCoincidences(String tag);

    void searchForums(String tag);

    interface View {
        void onNamesFound(ArrayList<String> tags);

        Context getContext();

        android.view.View getView();

        void onError();

        void onForumsFound(ArrayList<Forum> forums);
    }
}
