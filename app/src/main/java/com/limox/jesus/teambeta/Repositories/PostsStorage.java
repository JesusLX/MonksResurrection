package com.limox.jesus.teambeta.Repositories;

import com.limox.jesus.teambeta.Model.Post;

import java.util.ArrayList;

/**
 * Created by jesus on 11/11/16.
 */

public class PostsStorage {
    private static PostsStorage mPostsStorage;
    private ArrayList<Post> mPosts;
    private ArrayList<Post> mPostsPublished;
    private ArrayList<Post> mPostsNotPublished;
    private ArrayList<Post> mPostsFixed;

    public static PostsStorage get() {
        if (mPostsStorage == null) {
            mPostsStorage = new PostsStorage();
        }
        return mPostsStorage;
    }

    private PostsStorage() {
        mPosts = new ArrayList<>();
        mPostsPublished = new ArrayList<>();
        mPostsNotPublished = new ArrayList<>();
        mPostsFixed = new ArrayList<>();

        sortLists();
    }

    public ArrayList<Post> getPosts() {
        return mPosts;
    }

    /**
     * Sort all the lists of the class
     */
    private void sortLists() {
        clearLists();
        for (Post post : mPosts) {
            if (post.isFixed()) {
                mPostsFixed.add(post);
            } else if (post.isPublicate()) {
                mPostsPublished.add(post);
            } else {
                mPostsNotPublished.add(post);
            }
        }
    }

    private void clearLists() {
        mPostsFixed.clear();
        mPostsNotPublished.clear();
        mPostsPublished.clear();
    }


}
