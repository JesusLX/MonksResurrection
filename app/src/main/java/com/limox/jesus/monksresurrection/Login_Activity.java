package com.limox.jesus.monksresurrection;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;
import com.limox.jesus.monksresurrection.Validators.Validate;

public class Login_Activity extends Fragment {

    TextView mTxvSignUp;
    TextView mTxvFP;
    Button mBtnLogIn;
    EditText mEdtUserName;
    EditText mEdtPassword;
    String mUserName;
    String mPassword;
    AdapterView.OnClickListener mCliclListener;

    public static Login_Activity newInstance(){
        return new Login_Activity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View viewroot = inflater.inflate(R.layout.activity_login,container,false);
        if (viewroot != null){

            mTxvSignUp = (TextView) viewroot.findViewById(R.id.lgn_txvSignUp);
            mTxvFP = (TextView) viewroot.findViewById(R.id.lgn_txvFP);
            mBtnLogIn = (Button) viewroot.findViewById(R.id.lgn_btnSignIn);
            mEdtUserName = (EditText) viewroot.findViewById(R.id.lgn_edtUser);
            mEdtPassword = (EditText) viewroot.findViewById(R.id.lgn_edtPassword);

/*
            mCliclListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.lgn_txvFP:

                            startActivity(new Intent(Login_Activity.this, HelpLogin_Activity.class));
                            break;
                        case R.id.lgn_txvSignUp:
                            startActivity(new Intent(Login_Activity.this, StartSignUp_Activity.class));
                            break;
                        case R.id.lgn_btnSignIn:
                            if (validateAccount()) {
                                startActivity(new Intent(Login_Activity.this, Index_Activity.class));
                                // set the current user
                                Users_Singleton.get().setCurrentUser(Users_Singleton.get().getUser(mUserName));
                                //TODO añadir que se ponga aqui el usuario en los settings

                                finish();
                            }
                            break;
                    }
                }
            };
*/
            mTxvFP.setOnClickListener(mCliclListener);
            mTxvSignUp.setOnClickListener(mCliclListener);
            mBtnLogIn.setOnClickListener(mCliclListener);
        }
        return viewroot;
    }

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTxvSignUp = (TextView) findViewById(R.id.lgn_txvSignUp);
        mTxvFP = (TextView) findViewById(R.id.lgn_txvFP);
        mBtnLogIn = (Button) findViewById(R.id.lgn_btnSignIn);
        mEdtUserName = (EditText) findViewById(R.id.lgn_edtUser);
        mEdtPassword = (EditText) findViewById(R.id.lgn_edtPassword);


        mCliclListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.lgn_txvFP:

                        startActivity(new Intent(Login_Activity.this, HelpLogin_Activity.class));
                        break;
                    case R.id.lgn_txvSignUp:
                        startActivity(new Intent(Login_Activity.this, StartSignUp_Activity.class));
                        break;
                    case R.id.lgn_btnSignIn:
                        if (validateAccount()) {
                            startActivity(new Intent(Login_Activity.this, Index_Activity.class));
                            // set the current user
                            Users_Singleton.get().setCurrentUser(Users_Singleton.get().getUser(mUserName));
                            //TODO añadir que se ponga aqui el usuario en los settings

                            finish();
                        }
                        break;
                }
            }
        };

        mTxvFP.setOnClickListener(mCliclListener);
        mTxvSignUp.setOnClickListener(mCliclListener);
        mBtnLogIn.setOnClickListener(mCliclListener);
    }
*/
    private boolean validateAccount() {
        mUserName = mEdtUserName.getText().toString();
        mPassword = mEdtPassword.getText().toString();
        boolean allrigth = true;

        // check if the user introduced exists
        if (Users_Singleton.get().getUser(mUserName) != null) {
            // If is wrong show a message at the password edt
            if (Validate.validateAccount(mUserName, mPassword) != Validate.MESSAGE_OK) {
                mEdtPassword.setError(getResources().getString(Validate.validateAccount(mUserName, mPassword)));

                allrigth = false;
            }
        } else
            allrigth = false;
        return allrigth;
    }

}
