package com.limox.jesus.teambeta;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.Preferences;

public class Login_Activity extends AppCompatActivity implements UserManagerPresenter.View{

    TextView mTxvSignUp;
    TextView mTxvFP;
    Button mBtnLogIn;
    EditText mEdtUserName;
    EditText mEdtPassword;
    String mUserName;
    String mPassword;
    AdapterView.OnClickListener mClicKListener;
    UserManagerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_login);
        mTxvSignUp = (TextView) findViewById(com.limox.jesus.teambeta.R.id.lgn_txvSignUp);
        mTxvFP = (TextView) findViewById(com.limox.jesus.teambeta.R.id.lgn_txvFP);
        mBtnLogIn = (Button) findViewById(com.limox.jesus.teambeta.R.id.lgn_btnSignIn);
        mEdtUserName = (EditText) findViewById(com.limox.jesus.teambeta.R.id.lgn_edtUser);
        mEdtPassword = (EditText) findViewById(com.limox.jesus.teambeta.R.id.lgn_edtPassword);

        mPresenter = new UserManagerPresenterImpl(this);

        initializeClickListener();


        mTxvFP.setOnClickListener(mClicKListener);
        mTxvSignUp.setOnClickListener(mClicKListener);
        mBtnLogIn.setOnClickListener(mClicKListener);

    }

    private void initializeClickListener(){
        mClicKListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case com.limox.jesus.teambeta.R.id.lgn_txvFP:

                        startActivity(new Intent(Login_Activity.this, HelpLogin_Activity.class));
                        break;
                    case com.limox.jesus.teambeta.R.id.lgn_txvSignUp:
                        startActivity(new Intent(Login_Activity.this, SignUp_Activity.class));
                        break;
                    case com.limox.jesus.teambeta.R.id.lgn_btnSignIn:
                        validateAccount();
                        break;
                }
            }
        };
    }

    private void validateAccount() {
        mUserName = mEdtUserName.getText().toString();
        mPassword = mEdtPassword.getText().toString();

        // check if the user introduced exists



        mPresenter.getUser(mUserName, mPassword);



    }

    @Override
    public Context getContext() {
        return Login_Activity.this;
    }

    @Override
    public void showMessage(String message) {
        mEdtPassword.setError(message);
    }

    @Override
    public void onUserCreated() {

    }

    @Override
    public void onUserObtained(User user) {
        startActivity(new Intent(Login_Activity.this, Home_Activity.class));
        //startActivity(new Intent(Login_Activity.this, Navigator_Activity.class));

        // set the current user
        Users_Repository.get().setCurrentUser(user);

        Preferences.setCurrentUser(mUserName,mPassword,Login_Activity.this);

        finish();
    }


}