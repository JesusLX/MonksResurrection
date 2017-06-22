package com.limox.jesus.teambeta.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Presenter class to communicate the users manager views with the class who manage the users
 * Created by jesus on 2/03/17.
 */
public class UserManagerPresenterImpl implements UserManagerPresenter {

    private UserManagerPresenter.View view;
    private Context context;
    private User tryUser;

    private static final int getUserByName = 1;
    private static final int getUserByNamAndPass = 2;
    private static final int getUserById = 3;
    private int selection;

    public UserManagerPresenterImpl(View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void getUser(String idUser) {
        selection = getUserById;
        Bundle b = new Bundle();
        b.putString(AllConstants.Keys.SimpleBundle.ID_USER_KEY, idUser);
        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                view.onUserObtained(FirebaseContract.User.getUser(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //loadCursor(selection,b);
    }

    @Override
    public void getUser(String mUserName, String mPassword) {

    }

    @Override
    public void updateFirebaseEmail(String userKey, String email) {
        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(userKey).child(FirebaseContract.User.NODE_EMAIL).setValue(email);
    }

    @Override
    public void updateFirebaseName(String userKey, String name) {
        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(userKey).child(FirebaseContract.User.NODE_NAME).setValue(name);
    }

    @Override
    public void uploadPhoto(String iduser, Uri foto, OnSuccessListener<UploadTask.TaskSnapshot> successListener, OnFailureListener failureListener) {


        StorageReference storageRef = FirebaseStorage.getInstance().
                getReferenceFromUrl("gs://" + FirebaseContract.Storage.NAME);
        StorageReference imagesRef = storageRef.child(FirebaseContract.Storage.Images.ROOT_NAME + "/" + FirebaseContract.Storage.Images.PROFILES + "/" + iduser);
        try {
            Bitmap image = UIUtils.decodeUri(view.getContext(), foto, 200);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imagesRef.putBytes(data);
            uploadTask.addOnFailureListener(failureListener).addOnSuccessListener(successListener);
        } catch (FileNotFoundException e) {
            view.onError(e);
        }
    }


    @Override
    public void aggregateForum(String forumKey, final boolean admin, final ManagerView managerView) {
        (admin ? FirebaseContract.User.addForumAdmin(forumKey) : FirebaseContract.User.addForumParticipate(forumKey)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                managerView.onForumAggregated(admin);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                managerView.onError();
            }
        });
    }

    @Override
    public void updateUser(final String idUSer, final Uri userPhoto, final String userName, final String userEmail, final OnSuccessListener<Void> successListener, final OnSuccessListener<UploadTask.TaskSnapshot> successPhotoListener) {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (userPhoto != null) {
            uploadPhoto(idUSer, userPhoto, successPhotoListener, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    view.onError(e);
                }
            });

        }
        if (userName != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName).build();
            fUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                                Users_Repository.get().getCurrentUser().setName(userName);
                                updateFirebaseName(idUSer, userName);
                            } else {
                                view.onError(task.getException());
                            }
                        }
                    }).addOnSuccessListener(successListener);
        }
        if (userEmail != null) {

            fUser.updateEmail(userEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                                Users_Repository.get().getCurrentUser().setEmail(userEmail);
                                updateFirebaseEmail(idUSer, userEmail);
                                Preferences.setCurrentEmail(view.getContext(), userEmail);
                            } else {
                                view.onError(task.getException());
                            }
                        }
                    }).addOnSuccessListener(successListener);
        }


    }

    @Override
    public void getAllUsersOfForum(final String forumKey, final String mListName) {
        FirebaseContract.User.getUsersOfForum(forumKey, mListName, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    User tmp = FirebaseContract.User.getUser(user);
                    if (mListName.equals(FirebaseContract.User.NODE_FORUMS_ADMIN) ? tmp.getForumsAdmin().contains(forumKey) : tmp.getForumsWIParticipate().contains(forumKey)) {
                        tmp.setIdUser(user.getKey());
                        view.onUserObtained(tmp);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   /* @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch (selection){
            case getUserByName:
                if (cursor.getCount()<= 0){
                    try {
                        ContentValues contentValues;
                        contentValues = getContentUser(tryUser);
                        context.getContentResolver().insert(TeamBetaContract.Users.CONTENT_URI, contentValues);
                        view.onUserCreated();
                        view.onUserObtained(tryUser);
                    } catch (SQLException e) {
                        view.showMessage(e.getMessage());
                    }
                }else{
                    view.showMessage(context.getResources().getString(R.string.user_exists));
                }
                break;
            case getUserByNamAndPass:
                if (cursor.getCount()>0){
                    setUser(cursor);
                    view.onUserObtained(tryUser);
                }else
                    view.showMessage(context.getResources().getString(R.string.user_error));
                break;
            case getUserById:
                if (cursor.getCount()>0){
                    setUser(cursor);
                    view.onUserObtained(tryUser);
                }
                break;
        }
        ((Activity)context).getLoaderManager().destroyLoader(0);
    }
*/

    @Override
    public void addUser(User user) {

        //Encriptar la contraseña
        tryUser = user;

        getUser(user.getName());
    }


}
