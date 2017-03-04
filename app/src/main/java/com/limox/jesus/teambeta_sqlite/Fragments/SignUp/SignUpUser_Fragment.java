package com.limox.jesus.teambeta_sqlite.Fragments.SignUp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.limox.jesus.teambeta_sqlite.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta_sqlite.Model.User;
import com.limox.jesus.teambeta_sqlite.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta_sqlite.R;
import com.limox.jesus.teambeta_sqlite.Repositories.Users_Repository;
import com.limox.jesus.teambeta_sqlite.Utils.Preferences;
import com.limox.jesus.teambeta_sqlite.Validators.Validate;

public class SignUpUser_Fragment extends Fragment implements UserManagerPresenter.View{
    EditText edtUserName;
    EditText edtPassword;
    EditText edtRepeatPassword;
    Button btnCreateAcount;

    String email;
    String name;
    String password;
    String repeatPassword;

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
        Preferences.setCurrentUser(name,password,getContext());
        mCallback.startHomeActivity();

    }

    public interface OnSignUpUserFragmentListener{
        void startHomeActivity();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_sign_up_user,null);

        email = getArguments().getString("email");
        edtUserName = (EditText) rootView.findViewById(R.id.suu_edtName);
        edtPassword = (EditText) rootView.findViewById(R.id.suu_edtPassword);
        edtRepeatPassword = (EditText) rootView.findViewById(R.id.suu_edtRpPassword);
        btnCreateAcount = (Button) rootView.findViewById(R.id.suu_btnCreate);



        btnCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateParams()){
                    // if all right create user and use it like current user of the app
                    mPresenter.addUser(new User(name,email,password));
                }
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new UserManagerPresenterImpl(this);
    }

    private boolean validateParams(){
        boolean allRigth = true;

        name = edtUserName.getText().toString();
        password = edtPassword.getText().toString();
        repeatPassword = edtRepeatPassword.getText().toString();

        if (Validate.validateName(name) != Validate.MESSAGE_OK){
            edtUserName.setError(getResources().getString(Validate.validateName(name)));
            allRigth = false;
        }
        if (Validate.validatePassword(password) != Validate.MESSAGE_OK){
            edtPassword.setError(getResources().getString(Validate.validatePassword(password)));
            allRigth = false;
        }
        if (Validate.validateRepeatedPassword(password,repeatPassword) != Validate.MESSAGE_OK){
            edtRepeatPassword.setError(getResources().getString(Validate.validateRepeatedPassword(password,repeatPassword)));
            allRigth = false;
        }

        return allRigth;
    }
}
