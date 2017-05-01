package com.limox.jesus.teambeta.Presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Model.Post;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
        FirebaseDatabase.getInstance().getReference().
                child(FirebaseContract.Forums.ROOT_NODE).
                push().setValue(forum).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                task.getResult();
            }
        });
    }

    @Override
    public void uploadPhoto(Uri file, String folderName, String fileName) {


        StorageReference storageRef = FirebaseStorage.getInstance().
                getReferenceFromUrl("gs://" + mView.getContext().getString(R.string.app_name));
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
                orderByChild(FirebaseContract.Post.NODE_NAME).equalTo(forumsName).addValueEventListener(valueEventListener);
    }
}
