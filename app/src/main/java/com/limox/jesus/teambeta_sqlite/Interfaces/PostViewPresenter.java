package com.limox.jesus.teambeta_sqlite.Interfaces;

import android.support.annotation.IntDef;

import com.limox.jesus.teambeta_sqlite.Model.Post;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jesus on 30/12/16.
 */

public interface PostViewPresenter {
    interface View{
        @IntDef({TO_NOT_PUBLISHED,TO_PUBLISHED,TO_FIXED,DELETE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ACTION{}
        public static final int TO_NOT_PUBLISHED = 0;
        public static final int TO_PUBLISHED = 1;
        public static final int TO_FIXED = 2;
        public static final int DELETE = 3;
        void onDestroy();
    }
    void changePostOfList(int idPost, @Post.STATE int newState);

}
