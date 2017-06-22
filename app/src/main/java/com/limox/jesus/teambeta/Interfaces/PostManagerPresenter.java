package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.limox.jesus.teambeta.Model.Post;

/**
 * Created by jesus on 3/03/17.
 */

public interface PostManagerPresenter {

    String uploadPost(Post post, OnSuccessListener successListener);

    interface View{
        Context getContext();
    }
}
