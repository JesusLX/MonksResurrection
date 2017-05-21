package com.limox.jesus.teambeta.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.APIConstants;
import com.limox.jesus.teambeta.db.FirebaseContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
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

        HashMap<String, String> params = new HashMap<>();
        params.put(APIConstants.Forums.FORUM_KEY, forum.getKey());
        params.put(APIConstants.Forums.FORUM_NAME, forum.getName());
        params.put(APIConstants.Forums.FORUM_ADM_KEY, forum.getAdminsKey());
        params.put(APIConstants.Forums.FORUM_USR_KEY, forum.getUsersKey());
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
