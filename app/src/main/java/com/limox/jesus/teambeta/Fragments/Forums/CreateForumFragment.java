package com.limox.jesus.teambeta.Fragments.Forums;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.android.volley.Response;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.ForumManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.FirebaseContract;

import java.util.Arrays;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class CreateForumFragment extends Fragment implements ForumManagerPresenterImpl.View {

    private static final int INTENT_SELECT_IMAGE = 1;
    private Toolbar mToolbar;
    private ImageView mIvLogo;
    private EditText mEdtName, mEdtUsersKey, mEdtAdminsKey, mEdtDescription;

    private ForumManagerPresenter mPresenter;

    private boolean havImgSelected;
    private Uri imgSelected;
    private ProgressDialog loading;

    private OnFragmentInteractionListener mCallback;

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
        mEdtUsersKey = (EditText) rootView.findViewById(R.id.cf_edtname);
        mEdtAdminsKey = (EditText) rootView.findViewById(R.id.cf_edtAdminKey);
        mEdtDescription = (EditText) rootView.findViewById(R.id.cf_edtDescription);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setTitle(R.string.create_forum);
        mToolbar.inflateMenu(R.menu.menu_create_forum);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.action_send:
                        validate(mEdtName, mEdtAdminsKey, mEdtUsersKey);
                        break;
                }
            }
        });
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startForumsListFragment();
            }
        });

        mIvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, INTENT_SELECT_IMAGE);
            }
        });

    }
    private void validate(final EditText forumsName, final EditText adminsKey, final EditText userskey) {
        loading = new ProgressDialog(getContext());
        loading.setMessage(getString(R.string.loading));
        loading.setCancelable(false);
        loading.show();
        if (havImgSelected) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!userskey.getText().toString().isEmpty()) {
                        if (!adminsKey.getText().toString().isEmpty()) {
                            if (!forumsName.getText().toString().isEmpty()) {
                                mPresenter.existsForum(forumsName.getText().toString(), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (!Boolean.valueOf(response)) {
                                            mPresenter.uploadPhoto(imgSelected, Users_Repository.get().getCurrentUser().getId(), String.valueOf(System.currentTimeMillis()));
                                        } else {
                                            loading.dismiss();
                                            if (isAdded())
                                                forumsName.setError(getString(R.string.forums_name_exists));
                                        }
                                    }
                                });
                            }
                        } else {
                            loading.dismiss();
                            adminsKey.setError(getString(R.string.message_error_must_fill));
                        }
                    } else {
                        loading.dismiss();
                        userskey.setError(getString(R.string.message_error_must_fill));
                    }
                }
            }).run();
        } else {
            Snackbar.make(getView(), R.string.must_select_photo, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mCallback = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnForumViewFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onForumCreated(String forumKey) {
        loading.cancel();
        Users_Repository.get().getCurrentUser().getForumsOwn().add(forumKey);
        FirebaseContract.User.addForumOwn(forumKey);
        mCallback.startForumsListFragment();
    }

    @Override
    public void onImageUploaded(Uri downloadUrl) {
        Forum tmpforum = new Forum();
        tmpforum.setName(mEdtName.getText().toString().trim());
        tmpforum.setImgUrl(downloadUrl.toString());
        tmpforum.setOwnerId(Users_Repository.get().getCurrentUser().getId());
        tmpforum.setUsersKey(mEdtUsersKey.getText().toString().trim());
        tmpforum.setAdminsKey(mEdtAdminsKey.getText().toString().trim());
        tmpforum.setAdminsKey(mEdtAdminsKey.getText().toString().trim());
        tmpforum.setDescription(mEdtDescription.getText().toString().trim());
        tmpforum.setCreationDate(new Date());
        /*    if (!tmpforum.getTags().contains(mEdtName.getText().toString())) {
            tmpforum.getTags().add(mEdtName.getText().toString());
        }*/
        mPresenter.createForum(tmpforum);
    }

    @Override
    public void onImageFailed() {
        loading.cancel();
        Snackbar.make(getView(), R.string.error_upload_img, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onError() {
        loading.cancel();
    }

    @Override
    public void onDescriptionObtained(String description) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case INTENT_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    imgSelected = imageReturnedIntent.getData();
                    UIUtils.loadImage(getContext(), imgSelected.toString(), mIvLogo);
                    havImgSelected = true;
                    //mIvLogo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
    }

    public interface OnFragmentInteractionListener {
        void startForumsListFragment();
    }
}
