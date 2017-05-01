package com.limox.jesus.teambeta.Fragments.Forums;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.ForumManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class CreateForumFragment extends Fragment implements ForumManagerPresenterImpl.View {

    private static final int INTENT_SELECT_IMAGE = 1;
    private Toolbar mToolbar;
    private ImageView mIvLogo;
    private EditText mEdtName, mEdtUsersKey, mEdtAdminsKey, mEdtDescription, mEdtTags;
    private Button mBtnCreate;

    private ForumManagerPresenter mPresenter;

    private boolean havImgSelected;
    private Uri imgSelected;
    private ProgressDialog loading;

    private OnFragmentInteractionListener mListener;

    public CreateForumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ForumManagerPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_forum, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mIvLogo = (ImageView) rootView.findViewById(R.id.cf_ivLogo);
        mEdtName = (EditText) rootView.findViewById(R.id.cf_edtName);
        mEdtUsersKey = (EditText) rootView.findViewById(R.id.cf_edtUserKey);
        mEdtAdminsKey = (EditText) rootView.findViewById(R.id.cf_edtAdminKey);
        mEdtDescription = (EditText) rootView.findViewById(R.id.cf_edtDescription);
        mEdtTags = (EditText) rootView.findViewById(R.id.cf_edtTags);
        mBtnCreate = (Button) rootView.findViewById(R.id.cf_btnCreate);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setTitle(R.string.create_forum);
        mIvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, INTENT_SELECT_IMAGE);
            }
        });
        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(mEdtName, mEdtAdminsKey, mEdtUsersKey);
            }
        });
    }

    private void validate(final EditText forumsName, final EditText adminsKey, final EditText userskey) {
        loading = new ProgressDialog(getContext());
        loading.setTitle(R.string.loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!userskey.getText().toString().isEmpty()) {

                    if (!adminsKey.getText().toString().isEmpty()) {
                        if (!forumsName.getText().toString().isEmpty()) {
                            mPresenter.existsForum(forumsName.getText().toString(), new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount() == 0) {
                                        mPresenter.uploadPhoto(imgSelected, Users_Repository.get().getCurrentUser().getIdUser(), String.valueOf(System.currentTimeMillis()));
                                    } else {
                                        loading.cancel();
                                        forumsName.setError(getString(R.string.forums_name_exists));
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    loading.cancel();
                                }
                            });
                        }
                    } else {
                        loading.cancel();
                        adminsKey.setError(getString(R.string.message_error_must_fill));
                    }
                } else {
                    loading.cancel();
                    userskey.setError(getString(R.string.message_error_must_fill));
                }


            }
        }).run();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onForumCreated() {
        mListener.onForumCreated();
        loading.cancel();

    }

    @Override
    public void onImageUploaded(Uri downloadUrl) {
        mPresenter.createForum(new Forum(mEdtName.getText().toString().trim(), downloadUrl.toString(), mEdtUsersKey.getText().toString().trim(), mEdtAdminsKey.getText().toString().trim(), mEdtDescription.getText().toString(), mEdtTags.getText().toString().trim().split(",")));
    }

    @Override
    public void onImageFailed() {
        loading.cancel();
        Snackbar.make(getView(), R.string.error_upload_img, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case INTENT_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    imgSelected = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(imgSelected,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    mIvLogo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
    }

    public interface OnFragmentInteractionListener {
        void onForumCreated();
    }
}
