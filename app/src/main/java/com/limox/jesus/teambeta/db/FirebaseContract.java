package com.limox.jesus.teambeta.db;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ApiCallbacks;
import com.limox.jesus.teambeta.Model.Forum;
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

        public static void postUser(String id, com.limox.jesus.teambeta.Model.User user) {
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(id).setValue(user);
        }

        public static final String NODE_ID = "id";

        public static final String ROOT_NODE = "users";
        public static final String NODE_NAME = "name";
        public static final String NODE_PASSWORD = "password";
        public static final String NODE_EMAIL = "email";
        public static final String NODE_PHOTO_URL = "profilePicture";
        public static final String NODE_DELETED = "deleted";
        public static final String NODE_BLOCKED = "bloqued";
        public static final String NODE_FORUMS_ADMIN = "forums_admin";
        public static final String NODE_LIKED_POSTS = "liked_posts";

        public static com.limox.jesus.teambeta.Model.User getUser(DataSnapshot user) {
            com.limox.jesus.teambeta.Model.User tmp = user.getValue(com.limox.jesus.teambeta.Model.User.class);
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
    }


    public void get(HashMap<String, String> parameters, Context context, ApiCallbacks callback) {

    }


    public class Post {
        public static final String ROOT_NODE = "posts";

        public static final String NODE_STATE = "state";
        public static final String NODE_NAME = "name";
    }

    public class Storage {
        public class Images {
            public static final String ROOT_NAME = "images";
        }
    }

    public static class Forums {
        public static final String ROOT_NODE = "forum";

        public static void optForum(final String idForum, ValueEventListener valueEventListener) {
            FirebaseDatabase.getInstance().getReference().child(ROOT_NODE).child(idForum).addValueEventListener(valueEventListener);
        }
    }
}
