package com.limox.jesus.teambeta.Fragments.Forums;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.limox.jesus.teambeta.Interfaces.ForumManagerPresenter;
import com.limox.jesus.teambeta.Model.Forum;
import com.limox.jesus.teambeta.Presenter.ForumManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.db.FirebaseContract;
import com.theartofdev.edmodo.cropper.CropImage;

import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class CreateForumFragment extends Fragment implements ForumManagerPresenterImpl.View {

    private static final int INTENT_SELECT_IMAGE = 1;
    private Toolbar mToolbar;
    private ImageView mIvLogo, mIvColors;
    private EditText mEdtName, mEdtUsersKey, mEdtAdminsKey, mEdtDescription, mEdtWeb;
    private RelativeLayout mRlImageContent;
    private int selectedColorInt;
    private String selectedColorString;
    private ForumManagerPresenter mPresenter;

    private boolean havImgSelected;
    private Uri imgSelected;
    private ProgressDialog loading;
    private Forum mForum;

    private boolean isNew = false;

    private OnFragmentInteractionListener mCallback;

    public CreateForumFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Bundle forum) {
        CreateForumFragment fragment = new CreateForumFragment();
        fragment.setArguments(forum);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForum = getArguments().getParcelable(AllConstants.Keys.Parcelables.FORUM);
        }
        isNew = mForum == null;
        mPresenter = new ForumManagerPresenterImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_forum, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mIvLogo = (ImageView) rootView.findViewById(R.id.cf_ivLogo);
        mIvColors = (ImageView) rootView.findViewById(R.id.cf_ivColors);
        mEdtName = (EditText) rootView.findViewById(R.id.cf_edtName);
        mEdtUsersKey = (EditText) rootView.findViewById(R.id.cf_edtname);
        mEdtAdminsKey = (EditText) rootView.findViewById(R.id.cf_edtAdminKey);
        mEdtDescription = (EditText) rootView.findViewById(R.id.cf_edtDescription);
        mEdtWeb = (EditText) rootView.findViewById(R.id.cf_edtWeb);
        mRlImageContent = (RelativeLayout) rootView.findViewById(R.id.cf_rlLogo);
        return rootView;
    }

    private void setColors(int colorResource) {
        selectedColorInt = colorResource;
        selectedColorString = String.format("%06X", (0xFFFFFF & colorResource));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setColors(((ColorDrawable) mRlImageContent.getBackground()).getColor());
        if (!isNew) {
            mToolbar.setTitle(R.string.edit_forum);
            selectedColorString = mForum.getColor();
            mRlImageContent.setBackgroundColor(UIUtils.parseColor(selectedColorString));
            mEdtName.setText(mForum.getName());
            mEdtDescription.setText(mForum.getDescription());
            Glide.with(getContext()).load(mForum.getImgUrl()).into(mIvLogo);

            mEdtName.setText(mForum.getName());
            mEdtWeb.setText(mForum.getWeb());
            havImgSelected = true;
        } else
            mToolbar.setTitle(R.string.create_forum);
        mToolbar.inflateMenu(R.menu.menu_create_forum);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                UIUtils.hideKeyboard(getActivity(), getView());
                validate(mEdtName, mEdtAdminsKey, mEdtUsersKey);
                return true;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startForumsListFragment();
            }
        });
        final Fragment tmp = this;
        mIvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .start(getContext(), tmp);
                /* Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, INTENT_SELECT_IMAGE);*/
            }
        });
        mIvColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showColorPicker(getActivity(), new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        int colorFrom = selectedColorInt;
                        setColors(color);

                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, color);
                        colorAnimation.setDuration(400); // milliseconds
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                mRlImageContent.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorAnimation.start();
                    }
                }, isNew ? -1 : UIUtils.parseColor(mForum.getColor()));
            }
        });

    }

    private void validate(final EditText forumsName, final EditText adminsKey, final EditText userskey) {
        if (UIUtils.isNetworkAvailable(getContext())) {
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
                                            if (isNew)
                                                if (!Boolean.valueOf(response)) {
                                                    mPresenter.uploadPhoto(imgSelected, Users_Repository.get().getCurrentUser().getId(), String.valueOf(System.currentTimeMillis()));
                                                } else {
                                                    loading.dismiss();
                                                    if (isAdded())
                                                        forumsName.setError(getString(R.string.forums_name_exists));
                                                }
                                            else if (!Boolean.valueOf(response) || forumsName.getText().toString().equals(mForum.getName())) {
                                                if (imgSelected != null)
                                                    mPresenter.uploadPhoto(imgSelected, Users_Repository.get().getCurrentUser().getId(), String.valueOf(System.currentTimeMillis()));
                                                else {
                                                    onImageUploaded(null);
                                                }
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
                loading.dismiss();
                Snackbar.make(getView(), R.string.must_select_photo, Snackbar.LENGTH_LONG).show();
            }
        } else {
            UIUtils.toast(getContext(), getString(R.string.connection_error));
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
    public void onImageUploaded(Uri downloadUrl) {
        if (isAdded()) {
            if (mForum == null)
                mForum = new Forum();
            mForum.setName(mEdtName.getText().toString().trim());
            if (downloadUrl != null)
                mForum.setImgUrl(downloadUrl.toString());
            mForum.setOwnerId(Users_Repository.get().getCurrentUser().getId());
            mForum.setUsersKey(mEdtUsersKey.getText().toString().trim());
            mForum.setAdminsKey(mEdtAdminsKey.getText().toString().trim());
            mForum.setAdminsKey(mEdtAdminsKey.getText().toString().trim());
            mForum.setDescription(mEdtDescription.getText().toString().trim());
            mForum.setColor(selectedColorString);
            mForum.setWeb(mEdtWeb.getText().toString());
            mForum.setCreationDate(new Date());
        /*    if (!tmpforum.getTags().contains(mEdtName.getText().toString())) {
            tmpforum.getTags().add(mEdtName.getText().toString());
        }*/
            if (isNew)
                mPresenter.createForum(mForum);
            else
                mPresenter.updateForum(mForum);
        }
    }

    @Override
    public void onForumCreated(String forumKey) {
        if (isAdded()) {
            loading.dismiss();
            if (isNew) {
                Users_Repository.get().getCurrentUser().getForumsOwn().add(forumKey);
                FirebaseContract.User.addForumOwn(forumKey);
            }
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onImageFailed() {
        loading.cancel();
        UIUtils.snackBar(getView(), R.string.error_upload_img);
    }

    @Override
    public void onError() {
        loading.cancel();
    }

    @Override
    public void onFirebaseForumObtained(Forum optForum) {

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
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imgSelected = resultUri;
                    UIUtils.loadImage(getContext(), resultUri.toString(), mIvLogo);
                    havImgSelected = true;
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        void startForumsListFragment();
    }
}
