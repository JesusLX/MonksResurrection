package com.limox.jesus.teambeta.Interfaces;

import android.content.Context;
import android.database.Cursor;

import com.limox.jesus.teambeta.Model.Post;

import java.util.ArrayList;

/**
 * Created by jesus on 2/03/17.
 */

public interface PostsListPresenter {
    void getAllPost(@Post.STATE int typeList);

    void getAllPost(int all, int idUser);

    interface View {

        Context getContext();

        void setData(ArrayList<Post> posts);
    }
}
