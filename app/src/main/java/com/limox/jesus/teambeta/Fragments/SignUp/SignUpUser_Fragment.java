package com.limox.jesus.teambeta.Fragments.SignUp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.Utils.UIUtils;
import com.limox.jesus.teambeta.Utils.Validate;
import com.limox.jesus.teambeta.db.FirebaseContract;

public class SignUpUser_Fragment extends Fragment implements UserManagerPresenter.View{
    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtRepeatPassword;
    private Button btnCreateAccount;

    private String email;
    private String name;
    private String password;
    private String repeatPassword;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private UserManagerPresenter mPresenter;
    private OnSignUpUserFragmentListener mCallback;

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView(),message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onUserCreated() {
        //Users_Repository.get().addUser(name,password,email);
        Snackbar.make(getView(),"Usuario creado con exito",Snackbar.LENGTH_LONG).show();


    }

    @Override
    public void onUserObtained(User tryUser) {
        Users_Repository.get().setCurrentUser(tryUser);
        mCallback.startSelectProjectActivity();

    }

    @Override
    public void onError(Exception exception) {

    }

    public interface OnSignUpUserFragmentListener{
        void startSelectProjectActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSignUpUserFragmentListener)
            mCallback = (OnSignUpUserFragmentListener) activity;
        else
            throw new ClassCastException(activity.toString()+" must implement OnSignUpUserFragmentListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public static SignUpUser_Fragment newInstance(Bundle args) {

        SignUpUser_Fragment fragment = new SignUpUser_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mPresenter = new UserManagerPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sign_up_user,null);
        mProgress = new ProgressDialog(getContext());
        email = getArguments().getString("email");
        edtUserName = (EditText) rootView.findViewById(R.id.suu_edtName);
        edtPassword = (EditText) rootView.findViewById(R.id.suu_edtPassword);
        edtRepeatPassword = (EditText) rootView.findViewById(R.id.suu_edtRpPassword);
        btnCreateAccount = (Button) rootView.findViewById(R.id.suu_btnCreate);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.hideKeyboard(getActivity(), v);
                if (validateParams()){
                    mProgress.setMessage(getString(R.string.loading));
                    mProgress.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    mProgress.show();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        // Toast.makeText(getContext(), R.string.auth_failed,
                                        //      Toast.LENGTH_SHORT).show();
                                        UIUtils.snackBar(getView(), getString(R.string.connection_error));
                                    } else {
                                        FirebaseContract.User.postUser(task.getResult().getUser().getUid(), new User(name, email), new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                Preferences.setCurrentUser(task.getResult().getUser().getUid(), email, password, getContext());
                                                mPresenter.getUser(task.getResult().getUser().getUid());
                                            }
                                        });

                                    }

                                }
                            });
                    /*// if all right create user and use it like current user of the app
                    mPresenter.addUser(new User(name,email,password));*/
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out
                }
                // ...
            }
        };

    }

    private boolean validateParams(){
        boolean allRight = true;

        name = edtUserName.getText().toString();
        password = edtPassword.getText().toString();
        repeatPassword = edtRepeatPassword.getText().toString();

        if (Validate.validateName(name) != Validate.MESSAGE_OK){
            edtUserName.setError(getResources().getString(Validate.validateName(name)));
            allRight = false;
        }
        if (Validate.validatePassword(password) != Validate.MESSAGE_OK){
            edtPassword.setError(getResources().getString(Validate.validatePassword(password)));
            allRight = false;
        }
        if (Validate.validateRepeatedPassword(password,repeatPassword) != Validate.MESSAGE_OK){
            edtRepeatPassword.setError(getResources().getString(Validate.validateRepeatedPassword(password,repeatPassword)));
            allRight = false;
        }

        return allRight;
    }
}
