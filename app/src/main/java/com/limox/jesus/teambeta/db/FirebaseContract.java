package com.limox.jesus.teambeta.db;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ApiCallbacks;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.Preferences;

import java.util.HashMap;

/**
 * Created by usuario on 15/03/17.
 */

public class FirebaseContract {
    public interface FirebaseUserCallback {
        void onUserObtained(String idUser);

        void onUnsuccessful(Task<AuthResult> task);
    }

    public static class User {


        private static final String NODE_FAV_POSTS = "postsLiked";

        public static void postUser(String id, com.limox.jesus.teambeta.Model.User user, OnSuccessListener successListener) {
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(id).setValue(user).addOnSuccessListener(successListener);
        }

        public static final String NODE_ID = "id";

        public static final String ROOT_NODE = "users";
        public static final String NODE_NAME = "name";
        public static final String NODE_PASSWORD = "password";
        public static final String NODE_EMAIL = "email";
        public static final String NODE_PHOTO_URL = "profilePicture";
        public static final String NODE_DELETED = "deleted";
        public static final String NODE_BLOCKED = "bloqued";
        public static final String NODE_FORUMS_OWN = "forumsOwn";
        public static final String NODE_FORUMS_PARTICIPATE = "forumsWIParticipate";
        public static final String NODE_FORUMS_ADMIN = "forumsAdmin";
        public static final String NODE_LIKED_POSTS = "likedPosts";

        public static com.limox.jesus.teambeta.Model.User getUser(DataSnapshot user) {

            com.limox.jesus.teambeta.Model.User tmp = user.getValue(com.limox.jesus.teambeta.Model.User.class);
            //com.limox.jesus.teambeta.Model.User.fromJSON(user.getValue().toString());
            tmp.setIdUser(user.getKey());
            //tmp.setIdUser(user.getKey());
          /*  tmp.setEmail(user.getEmail());
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).get*/
            return tmp;
        }

        public static void loginUser(final String email, final String password, final Context context, Activity activity, final FirebaseUserCallback callback) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Preferences.setCurrentUser(task.getResult().getUser().getProviderId(), email, password, context);
                        if (callback != null)
                            callback.onUserObtained(task.getResult().getUser().getUid());
                    } else {
                        if (callback != null)
                            callback.onUnsuccessful(task);
                    }
                }

            });
        }

        public static Task<Void> addForumOwn(String forumKey) {
            return FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(Users_Repository.get().getCurrentUser().getIdUser()).child(NODE_FORUMS_OWN).setValue(Users_Repository.get().getCurrentUser().getForumsOwn());
        }

        public static Task<Void> addForumParticipate(String forumKey) {
            return FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(Users_Repository.get().getCurrentUser().getIdUser()).child(NODE_FORUMS_PARTICIPATE).setValue(Users_Repository.get().getCurrentUser().getForumsWIParticipate());
        }

        public static Task<Void> addForumAdmin(String forumKey) {
            return FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(Users_Repository.get().getCurrentUser().getIdUser()).child(NODE_FORUMS_ADMIN).setValue(Users_Repository.get().getCurrentUser().getForumsAdmin());
        }

        public static void addFavPost(String idPost) {
            Users_Repository.get().getCurrentUser().getPostsLiked().add(idPost);
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).
                    child(Users_Repository.get().getCurrentUser().getIdUser()).
                    child(NODE_FAV_POSTS).setValue(Users_Repository.get().getCurrentUser().getPostsLiked());
        }
    }


    public void get(HashMap<String, String> parameters, Context context, ApiCallbacks callback) {

    }


    public static class Posts {
        public static final String ROOT_NODE = "posts";

        public static final String NODE_STATE = "state";
        public static final String NODE_NAME = "name";
        public static final String NODE_OWNER = "idUser";
        private static final String NODE_SCORE = "score";
        public static final String NODE_TAGS = "tags";

        public static void changePostsList(String idPost, @com.limox.jesus.teambeta.Model.Post.STATE int state) {
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE).child(Users_Repository.get().getCurrentForum().getKey()).child(Posts.ROOT_NODE).child(idPost).child(Posts.NODE_STATE).setValue(state);

        }

        public static void deletePost(String idPost) {
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE).child(Users_Repository.get().getCurrentForum().getKey()).child(Posts.ROOT_NODE).child(idPost).removeValue();

        }

        public static void likePost(final String idPost, final Post posts) {
            FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE).child(Users_Repository.get().getCurrentForum().getKey()).child(Posts.ROOT_NODE).child(idPost).child(NODE_SCORE).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!Users_Repository.get().getCurrentUser().getPostsLiked().contains(idPost)) {
                        posts.setScore(1 + posts.getScore());
                       /* FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE).child(Users_Repository.get().getCurrentForum().getKey()).
                                child(FirebaseContract.Posts.ROOT_NODE).child(idPost).child(NODE_SCORE).setValue((Long) dataSnapshot.getValue() + 1);*/
                        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.Forums.ROOT_NODE).child(Users_Repository.get().getCurrentForum().getKey()).
                                child(Posts.ROOT_NODE).child(idPost).setValue(posts);
                        FirebaseContract.User.addFavPost(idPost);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public class Storage {
        public static final String NAME = "team-beta-f34f4.appspot.com";

        public class Images {
            public static final String ROOT_NAME = "images";
        }
    }

    public static class Forums {
        public static final String ROOT_NODE = "forum";
        public static final String NODE_DESCRIPTION = "description";

        public static void optForum(final String idForum, ValueEventListener valueEventListener) {
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(idForum).addValueEventListener(valueEventListener);
        }

        public static void getDescription(String key, ChildEventListener listener) {
            FirebaseDatabase.getInstance().getReference().
                    child(ROOT_NODE).
                    child(key).
                    child(NODE_DESCRIPTION).
                    addChildEventListener(listener);
        }
    }
}
