package com.limox.jesus.monksresurrection.Fragments.SignUp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;
import com.limox.jesus.monksresurrection.Utils.Preferences;
import com.limox.jesus.monksresurrection.Validators.Validate;

public class SignUpUser_Fragment extends Fragment {
    EditText edtUserName;
    EditText edtPassword;
    EditText edtRepeatPassword;
    Button btnCreateAcount;

    String email;
    String name;
    String password;
    String repeatPassword;

    private OnSignUpUserFragmentListener mCallback;

    public interface OnSignUpUserFragmentListener{
        void startHomeActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignUpUserFragmentListener)
            mCallback = (OnSignUpUserFragmentListener) context;
        else
            throw new ClassCastException(context.toString()+" must implement OnSignUpUserFragmentListener");
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
                    Users_Singleton.get().addUser(name,password,email);
                    Users_Singleton.get().setCurrentUser(Users_Singleton.get().getUser(name));
                    Preferences.setCurrentUser(name,password,getContext());

                    mCallback.startHomeActivity();

                }
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
