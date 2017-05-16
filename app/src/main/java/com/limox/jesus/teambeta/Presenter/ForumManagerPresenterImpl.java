package com.limox.jesus.teambeta.Presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

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
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * Created by Jesus on 27/04/2017.
 */

public class ForumManagerPresenterImpl implements ForumManagerPresenter {

    ForumManagerPresenter.View mView;

    public ForumManagerPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void createForum(Forum forum) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                push();
        databaseReference.setValue(forum);
        mView.onForumCreated(databaseReference.getKey());
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
    public void existsForum(String forumsName, ValueEventListener valueEventListener) {
        FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                orderByChild(FirebaseContract.Posts.NODE_NAME).
                equalTo(forumsName).addListenerForSingleValueEvent(valueEventListener);
    }
}
