package com.limox.jesus.teambeta;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.limox.jesus.teambeta.Interfaces.UserManagerPresenter;
import com.limox.jesus.teambeta.Model.User;
import com.limox.jesus.teambeta.Presenter.UserManagerPresenterImpl;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.db.FirebaseContract;


public class Login_Activity extends AppCompatActivity implements UserManagerPresenter.View{

    private TextView mTxvSignUp;
    private TextView mTxvFP;
    private Button mBtnLogIn;
    private EditText mEdtUserName;
    private EditText mEdtPassword;
    private String mUserName;
    private String mPassword;
    private AdapterView.OnClickListener mClickListener;
    private UserManagerPresenter mPresenter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTxvSignUp = (TextView) findViewById(com.limox.jesus.teambeta.R.id.lgn_txvSignUp);
        mTxvFP = (TextView) findViewById(com.limox.jesus.teambeta.R.id.lgn_txvFP);
        mBtnLogIn = (Button) findViewById(com.limox.jesus.teambeta.R.id.lgn_btnSignIn);
        mEdtUserName = (EditText) findViewById(com.limox.jesus.teambeta.R.id.lgn_edtUser);
        mEdtPassword = (EditText) findViewById(com.limox.jesus.teambeta.R.id.lgn_edtPassword);

        mPresenter = new UserManagerPresenterImpl(this);

        initializeClickListener();
        mFirebaseAuth = FirebaseAuth.getInstance();

        /*mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fUser =firebaseAuth.getCurrentUser();
                if (fUser != null){
              //      FirebaseDatabase.getInstance().
                    //validateAccount();
                }else{
                        startActivityForResult(
                           AuthUI.getInstance()
                                   .createSignInIntentBuilder()
                                   .setIsSmartLockEnabled(false)
                                   .setProviders(
                                           AuthUI.EMAIL_PROVIDER,
                                           AuthUI.GOOGLE_PROVIDER)
                                   .build(),
                           RC_SIGN_IN);
                }
            }
        };
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);*/

        mTxvFP.setOnClickListener(mClickListener);
        mTxvSignUp.setOnClickListener(mClickListener);
        mBtnLogIn.setOnClickListener(mClickListener);

    }

    private void initializeClickListener(){
        mClickListener = new View.OnClickListener() {
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
        FirebaseAuth.getInstance().signInWithEmailAndPassword(mUserName, mPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mPresenter.getUser(task.getResult().getUser().getUid());
                    /*User user = FirebaseContract.User.getUser(task.getResult().getUser());
                    Users_Repository.get().setCurrentUser(user);

                    Preferences.setCurrentUser(user.getIdUser(), mUserName,mPassword,Login_Activity.this);*/
                }
            }
        });

        //mPresenter.getUser(mUserName, mPassword);
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

        Preferences.setCurrentUser(user.getIdUser(), mUserName, mPassword, Login_Activity.this);

        finish();
    }


}