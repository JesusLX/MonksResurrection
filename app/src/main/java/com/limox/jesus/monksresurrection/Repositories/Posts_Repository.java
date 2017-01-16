package com.limox.jesus.monksresurrection.Repositories;

import com.limox.jesus.monksresurrection.Model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jesus on 11/11/16.
 */

public class Posts_Repository {
    private static Posts_Repository mPosts_Repository;
    private ArrayList<Post> mPosts;
    private ArrayList<Post> mPostsPublished;
    private ArrayList<Post> mPostsNotPublished;
    private ArrayList<Post> mPostsFixed;

    public static Posts_Repository get() {
        if (mPosts_Repository == null) {
            mPosts_Repository = new Posts_Repository();
        }
        return mPosts_Repository;
    }

    private Posts_Repository() {
        mPosts = new ArrayList<>();
        mPostsPublished = new ArrayList<>();
        mPostsNotPublished = new ArrayList<>();
        mPostsFixed = new ArrayList<>();


        createExamplePosts();
        sortLists();
    }

    private void createExamplePosts() {
        mPosts.add(new Post("Error1", 0, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,fallo", 0));
        mPosts.add(new Post("Error2", 0, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,fallo", 1));
        mPosts.add(new Post("Error3", 1, "Se ha rotoblablalbalablablablalbablablalba", "prueba,pj,fallo", 2));
        mPosts.add(new Post("Error4", 2, "Se ha rotoblablalbalablablablalbablablalba", "prueba,error,noche", 3));
        mPosts.add(new Post("Error5", 0, "Se ha castillo jajajalalba", "prueba,castillo,fallo", 4));
        mPosts.add(new Post("Error6", 1, "Se ha rotoblablalbalablablablalbablablalba", "pruebas,errores,fallo", 5));
        mPosts.add(new Post(6, "Error7", 1, "Se me ha cerrado a tope", Post.FIXED, "salto,apagado,pff"));
        mPosts.add(new Post(7, "Error8", 1, "No me puedo pasar la parte x", Post.FIXED, "dragon,noche,fuego"));
        mPosts.add(new Post(8, "Error9", 2, "Bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla" +
                " bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla "
                , Post.FIXED, "salto,apagado,pff"));
        mPosts.add(new Post(9, "Error10", 0, "Se me ha cerrado a tope", Post.PUBLISHED, "salto,apagado,pff"));
        mPosts.add(new Post(10, "Error11", 0, "Se me ha cerrado a tope", Post.FIXED, "salto,apagado,pff"));
        mPosts.add(new Post(11, "Error12", 1, "Se me ha cerrado a tope", Post.PUBLISHED, "salto,apagado,pff"));
    }

    private boolean addPost(Post post) {
        boolean allFine = false;
        if (!mPosts.contains(post)) {
            mPosts.add(post);
            mPostsNotPublished.add(post);
            allFine = true;
            sortLists();
        }
        return allFine;
    }

    public ArrayList<Post> getPosts() {
        return mPosts;
    }

    public ArrayList<Post> getPostsPublished(Comparator comparator) {
        if (comparator != null)
            Collections.sort(mPostsPublished,comparator);
        return mPostsPublished;
    }

    public ArrayList<Post> getPostsNotPublished(Comparator comparator) {
        if (comparator != null)
            Collections.sort(mPostsPublished,comparator);

            return mPostsNotPublished;
    }

    public ArrayList<Post> getPostsFixed(Comparator comparator) {
        if (comparator != null)
            Collections.sort(mPostsPublished,comparator);
        return mPostsFixed;
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

    public boolean deletePost(int idPost) {
        boolean allFine = false;
        Post post = new Post(idPost);
        if (mPosts.contains(post)) {
            mPosts.remove(post);
            allFine = true;
            sortLists();
        }
        return allFine;
    }

    /**
     * get a post by his id
     *
     * @param idPost id of the post you want to get
     * @return a post who coincide with the id intrude
     */
    public Post getPost(int idPost) {
        return mPosts.get(mPosts.indexOf(new Post(idPost)));
    }

    private void clearLists() {
        mPostsFixed.clear();
        mPostsNotPublished.clear();
        mPostsPublished.clear();
    }

    /**
     * Create a new post who is send to the admins
     * @param title tilte of the post
     * @param description description of the post
     * @param tags tags separated by "," who identify the post
     */
    public void createPost(String title, String description, String tags) {
        mPosts.add(new Post(title, Users_Repository.get().getCurrentUser().getIdUser(), description, tags, mPosts.size()));
        sortLists();
    }

    /**
     * Create a new post already published
     * @param title tilte of the post
     * @param description description of the post
     * @param tags tags separated by "," who identify the post
     */
    public void createPostPublished(String title, String description, String tags) {
        mPosts.add(new Post(mPosts.size(), title, Users_Repository.get().getCurrentUser().getIdUser(), description, Post.PUBLISHED, tags));
        sortLists();
    }

    public ArrayList<Post> getPostsByUser(int idUser, @Post.STATE int typeList) {
        ArrayList<Post> postsOfUser = new ArrayList<>();

        for (Post tmp : mPosts) {
            if (tmp.getIdUser() == idUser) {
                if (typeList == Post.ALL)
                    postsOfUser.add(tmp);
                else if (tmp.getState() == typeList)
                    postsOfUser.add(tmp);
            }
        }
        Collections.sort(postsOfUser,Post.LAST_FIRST);
        return postsOfUser;

    }

    public void changePostOfList(int idPost, @Post.STATE int newState) {
        Post tmpPost;
        tmpPost = getPost(idPost);
        tmpPost.setState(newState);
        sortLists();
    }
}
