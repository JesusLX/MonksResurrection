package com.limox.jesus.teambeta.Fragments.Settings;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.Utils.Validate;
import com.limox.jesus.teambeta.db.FirebaseContract;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class EditUser_Fragment extends Fragment implements UserManagerPresenter.View {

    private static final int INTENT_SELECT_IMAGE = 1;
    private User mUser;
    private Toolbar mToolbar;
    private CircleImageView mCivUser;
    private EditText mEdtUserName, mEdtEmail;
    private UserManagerPresenterImpl mPresenter;
    private boolean havImgSelected;
    private Uri imgSelected;
    private ProgressDialog loading;


    public EditUser_Fragment() {
        // Required empty public constructor
    }


    public static EditUser_Fragment newInstance(Bundle user) {
        EditUser_Fragment fragment = new EditUser_Fragment();
        fragment.setArguments(user);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(AllConstants.Keys.Parcelables.USER_PARCELABLE_KEY);
        }
        mPresenter = new UserManagerPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_user, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mCivUser = (CircleImageView) rootView.findViewById(R.id.civUser);
        mEdtUserName = (EditText) rootView.findViewById(R.id.edtUserName);
        mEdtEmail = (EditText) rootView.findViewById(R.id.edtEmail);
        loading = new ProgressDialog(getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setTitle(R.string.edit_profile);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_create_forum);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_send:
                        UIUtils.hideKeyboard(getActivity(), getView());
                        validate();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        Glide.with(getContext()).load(mUser.getProfilePicture()).into(mCivUser);
        mEdtUserName.setText(mUser.getName());
        mEdtEmail.setText(mUser.getEmail());
        final Fragment tmp = this;
        mCivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .start(getContext(), tmp);
            }
        });
    }

    private void validate() {
        boolean cancontinue = true;
        Uri photo = null;
        String name = null;
        String email = null;
        final int[] nChanges = {0};
        if (imgSelected != null)
            if (!mUser.getProfilePicture().equals(imgSelected.toString())) {
                photo = imgSelected;
                nChanges[0]++;
            }
        if (!mUser.getName().equals(mEdtUserName.getText().toString())) {
            int rm = Validate.validateName(mEdtUserName.getText().toString());
            if (rm != Validate.MESSAGE_OK) {
                cancontinue = false;
                mEdtUserName.setError(getString(rm));
            } else {
                name = mEdtUserName.getText().toString();
                nChanges[0]++;
            }
        }
        if (!mUser.getEmail().equals(mEdtEmail.getText().toString())) {
            int rm = Validate.validateEmail(mEdtEmail.getText().toString());
            if (rm != Validate.MESSAGE_OK) {
                cancontinue = false;
                mEdtEmail.setError(getString(rm));
            } else {
                email = mEdtEmail.getText().toString();
                nChanges[0]++;
            }
        }
        if (cancontinue && nChanges[0] > 0) {
            showPasswordDialog(photo, name, email, nChanges);
        }
    }

    private void showPasswordDialog(final Uri photo, final String name, final String email, final int[] nChanges) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit_profile);
        builder.setMessage(getString(R.string.enter_pass_to_continue));
        final EditText edtKey = new EditText(getContext());
        edtKey.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(edtKey);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loading.setMessage(getString(R.string.progress_dialog_sending));
                loading.show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(mUser.getEmail(), edtKey.getText().toString());

                user.reauthenticate(credential)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mPresenter.updateUser(Users_Repository.get().getCurrentUser().getId(), photo, name, email, new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void o) {
                                        nChanges[0]--;
                                        if (nChanges[0] == 0) {
                                            loading.dismiss();
                                            getActivity().onBackPressed();
                                        }
                                    }
                                }, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                        nChanges[0]--;
                                        FirebaseDatabase.getInstance().getReference().child(FirebaseContract.User.ROOT_NODE).child(Users_Repository.get().getCurrentUser().getId()).child(FirebaseContract.User.NODE_PHOTO_URL).setValue(taskSnapshot.getDownloadUrl().toString());
                                        Users_Repository.get().getCurrentUser().setProfilePicture(taskSnapshot.getDownloadUrl().toString());
                                        if (nChanges[0] <= 0) {
                                            loading.dismiss();
                                            getActivity().onBackPressed();
                                        }
                                    }
                                });
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.dismiss();
                        UIUtils.snackBar(getView(), R.string.wrong_password);
                    }
                });
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User tryUser) {

    }

    @Override
    public void onError(Exception exception) {
        loading.dismiss();
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case INTENT_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    imgSelected = imageReturnedIntent.getData();
                    UIUtils.loadImage(getContext(), imgSelected.toString(), mCivUser);
                    havImgSelected = true;
                    //mIvLogo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(imageReturnedIntent);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imgSelected = resultUri;
                    UIUtils.loadImage(getContext(), resultUri.toString(), mCivUser);
                    havImgSelected = true;
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
        }
    }
}
