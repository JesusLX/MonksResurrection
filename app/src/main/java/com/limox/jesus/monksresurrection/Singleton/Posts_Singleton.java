package com.limox.jesus.monksresurrection.Singleton;

import com.limox.jesus.monksresurrection.Model.Post;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 11/11/16.
 */

public class Posts_Singleton {
    private static Posts_Singleton mPosts_Singleton;
    private List<Post> mPosts;
    private List<Post> mPostsPublished;
    private List<Post> mPostsNotPublished;
    private List<Post> mPostsFixed;

    public static Posts_Singleton getPosts_Singleton() {
        if (mPosts_Singleton == null) {
            mPosts_Singleton = new Posts_Singleton();
        }
        return mPosts_Singleton;
    }

    private Posts_Singleton() {
        mPosts = new ArrayList<Post>();
        mPostsPublished = new ArrayList<Post>();
        mPostsNotPublished = new ArrayList<Post>();
        mPostsFixed = new ArrayList<Post>();


        createExamplePosts();
        sortLists();
    }

    private void createExamplePosts() {
        mPosts.add(new Post("Error1", 0, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,fallo", 0));
        mPosts.add(new Post("Error2", 0, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,fallo", 1));
        mPosts.add(new Post("Error3", 1, "Se ha rotoblablalbalablablablalbablablalba", "prueba,pj,fallo", 2));
        mPosts.add(new Post("Error4", 2, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,noche", 3));
        mPosts.add(new Post("Error5", 0, "Se ha castillo jajajalalba", "prueba,castillo,fallo", 4));
        mPosts.add(new Post("Error6", 3, "Se ha rotoblablalbalablablablalbablablalba", "pruebas,errores,fallo", 5));
    }

    private boolean addPost(Post post) {
        boolean allFine = false;
        if (!mPosts.contains(post)) {
            mPosts.add(post);
            mPostsNotPublished.add(post);
            allFine = true;
        }
        return allFine;
    }
    public boolean toPublicPost(Post post){
        boolean allFine = false;
        if (mPosts.contains(post)) {
            if (mPostsNotPublished.contains(post)){
                mPostsNotPublished.remove(mPostsNotPublished.indexOf(post));
                mPostsPublished.add(post);
                allFine = true;
            }
        }
        return allFine;
    }
    public boolean toFixPost(Post fixedPost){
        boolean allFine = false;
        if (mPosts.contains(fixedPost)) {
            if (mPostsPublished.contains(fixedPost)){
                mPostsPublished.remove(mPostsPublished.indexOf(fixedPost));
                mPostsFixed.add(fixedPost);
                allFine = true;
            }
        }
        return allFine;
    }

    public List<Post> getPosts() {
        return mPosts;
    }

    public List<Post> getPostsPublished() {
        return mPostsPublished;
    }

    public List<Post> getPostsNotPublished() {
        return mPostsNotPublished;
    }

    public List<Post> getPostsFixed() {
        return mPostsFixed;
    }
    private void sortLists(){
        clearLists();
        for (Post post:mPosts) {
            if (post.isFixed()){
                mPostsFixed.add(post);
            }else if (post.isPublicate()){
                mPostsPublished.add(post);
            }else {
                mPostsNotPublished.add(post);
            }

        }
    }
    private void clearLists(){
        mPostsFixed.clear();
        mPostsNotPublished.clear();
        mPostsPublished.clear();
    }
}
