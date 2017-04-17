package com.limox.jesus.teambeta.db;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.limox.jesus.teambeta.Interfaces.ApiCallbacks;
import com.limox.jesus.teambeta.Model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by usuario on 15/03/17.
 */

public class FirebaseContract {

    public static class User {
        public static void postUser(String id, com.limox.jesus.teambeta.Model.User user) {
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(id).setValue(user);

           /* for (Map.Entry<String, String> entry : user.getHashMap().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(id).child(key).setValue(value);
            }
            if (user.getForumsAdmin() != null)
            for (String forumsAdmin: user.getForumsAdmin()) {
                FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(id).child(NODE_FORUMS_ADMIN).setValue(forumsAdmin);
            }
            if (user.getPostsLiked() != null)
            for (String postsliked: user.getPostsLiked()) {
                FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(id).child(NODE_LIKED_POSTS).setValue(postsliked);
            }*/
        }

        public static final String NODE_ID = "id";

        public static final String ROOT_NODE = "users";
        public static final String NODE_NAME = "name";
        public static final String NODE_PASSWORD = "password";
        public static final String NODE_EMAIL = "email";
        public static final String NODE_PHOTO_URL = "photo_url";
        public static final String NODE_DELETED = "deleted";
        public static final String NODE_BLOCKED = "bloqued";
        public static final String NODE_FORUMS_ADMIN = "forums_admin";
        public static final String NODE_LIKED_POSTS = "liked_posts";

        public static com.limox.jesus.teambeta.Model.User getUser(DataSnapshot user) {
            com.limox.jesus.teambeta.Model.User tmp = user.getValue(com.limox.jesus.teambeta.Model.User.class);
            //tmp.setIdUser(user.getKey());
          /*  tmp.setEmail(user.getEmail());
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).get*/
            return tmp;
        }
    }

    public void get(HashMap<String, String> parameters, Context context, ApiCallbacks callback) {

    }


    public class Post {
        public static final String ROOT_NODE = "posts";

        public static final String NODE_STATE = "state";
    }
}
