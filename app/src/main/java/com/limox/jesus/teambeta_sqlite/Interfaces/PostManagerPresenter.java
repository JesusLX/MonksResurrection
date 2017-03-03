package com.limox.jesus.teambeta_sqlite.Interfaces;

import android.content.Context;

import com.limox.jesus.teambeta_sqlite.Model.Post;

/**
 * Created by jesus on 3/03/17.
 */

public interface PostManagerPresenter {

    void uploadPost(Post post);

    interface View{
        Context getContext();
    }
}
