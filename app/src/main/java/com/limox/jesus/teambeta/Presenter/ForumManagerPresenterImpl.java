package com.limox.jesus.teambeta.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Utils.AeSimpleSHA1;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.APIConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Presenter class to manage Forums
 * Created by Jesus on 27/04/2017.
 */
public class ForumManagerPresenterImpl implements ForumManagerPresenter {

    ForumManagerPresenter.View mView;

    public ForumManagerPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void createForum(final Forum forum) {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                push();
        forum.setKey(databaseReference.getKey());
        databaseReference.child(FirebaseContract.Forums.NODE_DESCRIPTION).setValue(forum.getDescription());
        databaseReference.child(FirebaseContract.Forums.NODE_COLOR).setValue(forum.getColor());
        databaseReference.child(FirebaseContract.Forums.NODE_WEB).setValue(forum.getWeb());

        HashMap<String, String> params = new HashMap<>();
        params.put(APIConstants.Forums.FORUM_KEY, forum.getKey());
        params.put(APIConstants.Forums.FORUM_NAME, forum.getName());
        params.put(APIConstants.Forums.FORUM_ADM_KEY, AeSimpleSHA1.SHA1(forum.getAdminsKey()));
        params.put(APIConstants.Forums.FORUM_USR_KEY, AeSimpleSHA1.SHA1(forum.getUsersKey()));
        params.put(APIConstants.Forums.FORUM_CREATION_D, String.valueOf(forum.getCreationDate().getTime()));
        params.put(APIConstants.Forums.FORUM_DELETED, String.valueOf(forum.getDeleted() ? 1 : 0));
        params.put(APIConstants.Forums.FORUM_IMG_URL, forum.getImgUrl());
        params.put(APIConstants.Forums.FORUM_OWNER_ID, forum.getOwnerId());

        APIConstants.Forums.postForum(mView.getContext(), params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mView.onForumCreated(databaseReference.getKey());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                FirebaseDatabase.getInstance().getReference().
                        child(FirebaseContract.Forums.ROOT_NODE).child(forum.getKey()).removeValue();
                mView.onError();

            }
        });
    }

    @Override
    public void updateForum(final Forum forum) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                child(forum.getKey());
        databaseReference.child(FirebaseContract.Forums.NODE_DESCRIPTION).setValue(forum.getDescription());
        databaseReference.child(FirebaseContract.Forums.NODE_COLOR).setValue(forum.getColor());
        databaseReference.child(FirebaseContract.Forums.NODE_WEB).setValue(forum.getWeb());

        HashMap<String, String> params = new HashMap<>();
        params.put(APIConstants.Forums.FORUM_KEY, forum.getKey());
        params.put(APIConstants.Forums.FORUM_NAME, forum.getName());
        params.put(APIConstants.Forums.FORUM_ADM_KEY, AeSimpleSHA1.SHA1(forum.getAdminsKey()));
        params.put(APIConstants.Forums.FORUM_USR_KEY, AeSimpleSHA1.SHA1(forum.getUsersKey()));
        params.put(APIConstants.Forums.FORUM_IMG_URL, forum.getImgUrl());

        APIConstants.Forums.updateForum(mView.getContext(), forum.getKey(), params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mView.onForumCreated(databaseReference.getKey());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mView.onError();

            }
        });
    }

    @Override
    public void getForumFirebaseData(final Forum forum) {
        if (forum != null) {
            FirebaseDatabase.getInstance().getReference().
                    child(FirebaseContract.Forums.ROOT_NODE).
                    child(forum.getKey()).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(FirebaseContract.Forums.NODE_DESCRIPTION).getValue()
                                    != null)
                                forum.setDescription(dataSnapshot.child(FirebaseContract.Forums.NODE_DESCRIPTION).getValue().toString());
                            if (dataSnapshot.child(FirebaseContract.Forums.NODE_COLOR).getValue() != null)
                                forum.setColor(dataSnapshot.child(FirebaseContract.Forums.NODE_COLOR).getValue().toString());
                            if (dataSnapshot.child(FirebaseContract.Forums.NODE_WEB).getValue() != null)
                                forum.setWeb(dataSnapshot.child(FirebaseContract.Forums.NODE_WEB).getValue().toString());
                            mView.onFirebaseForumObtained(forum);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

    }

    @Override
    public void uploadPhoto(Uri file, String folderName, String fileName) {


        StorageReference storageRef = FirebaseStorage.getInstance().
                getReferenceFromUrl("gs://" + FirebaseContract.Storage.NAME);
        StorageReference imagesRef = storageRef.child(FirebaseContract.Storage.Images.ROOT_NAME + "/" + folderName + "/" + fileName);
        try {
            Bitmap image = UIUtils.decodeUri(mView.getContext(), file, 200);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    mView.onImageFailed();
                    return;
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mView.onImageUploaded(taskSnapshot.getDownloadUrl());
                }
            });
        } catch (FileNotFoundException e) {
            mView.onImageFailed();
        }
    }

    @Override
    public void existsForum(String forumsName, Response.Listener<String> valueEventListener) {
        APIConstants.Forums.existsForum(mView.getContext(), forumsName, valueEventListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.onError();
            }
        });
    }


}
