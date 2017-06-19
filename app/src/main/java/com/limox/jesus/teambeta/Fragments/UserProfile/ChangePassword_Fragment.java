package com.limox.jesus.teambeta.Fragments.UserProfile;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.Utils.Validate;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChangePassword_Fragment extends Fragment implements UserManagerPresenter.View {


    public ChangePassword_Fragment() {
        // Required empty public constructor
    }

    private User mUser;
    private Toolbar mToolbar;
    private CircleImageView mCivUser;
    private TextView mTxvUserName;
    private EditText mEdtCurrentPassword, mEdtPassword, mEdtRepPassword;
    private ProgressDialog progress;

    public static ChangePassword_Fragment newInstance(Bundle user) {
        ChangePassword_Fragment fragment = new ChangePassword_Fragment();
        fragment.setArguments(user);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(AllConstants.Keys.Parcelables.USER_PARCELABLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mCivUser = (CircleImageView) rootView.findViewById(R.id.civUser);
        mTxvUserName = (TextView) rootView.findViewById(R.id.txvUserName);
        mEdtCurrentPassword = (EditText) rootView.findViewById(R.id.edtCurrentPass);
        mEdtPassword = (EditText) rootView.findViewById(R.id.edtPassword);
        mEdtRepPassword = (EditText) rootView.findViewById(R.id.edtRpPassword);
        progress = new ProgressDialog(getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setTitle(R.string.change_password);
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
        mTxvUserName.setText(mUser.getName());
    }

    private void validate() {
        if (!mEdtCurrentPassword.getText().toString().isEmpty()) {
            if (mEdtPassword.getText().toString().equals(mEdtRepPassword.getText().toString())) {
                int res = Validate.validatePassword(mEdtPassword.getText().toString());
                if (res != Validate.MESSAGE_OK) {
                    mEdtPassword.setError(getString(res));
                } else {
                    progress.setMessage(getString(R.string.loading));
                    progress.show();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(mUser.getEmail(), mEdtCurrentPassword.getText().toString());

                    if (user != null)
                        user.reauthenticate(credential)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        user.updatePassword(mEdtPassword.getText().toString())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Preferences.setCurrentPassword(getContext(), mEdtPassword.getText().toString());
                                                        getActivity().onBackPressed();
                                                        progress.dismiss();
                                                    }
                                                });
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                UIUtils.snackBar(getView(), R.string.wrong_password);
                                progress.dismiss();
                            }
                        });
                    else
                        progress.dismiss();
                }
            }
        } else {
            progress.dismiss();
            UIUtils.snackBar(getView(), R.string.enter_pass_to_continue);
        }

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
        progress.dismiss();
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}



