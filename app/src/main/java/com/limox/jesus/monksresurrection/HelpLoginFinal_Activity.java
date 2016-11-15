package com.limox.jesus.monksresurrection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;

public class HelpLoginFinal_Activity extends AppCompatActivity {

    String mEmail;
    User mUser;
    TextView mTxvUserName;
    TextView mTxvUserPassword;
    ImageView mIvImageProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_login_final);

        mTxvUserName = (TextView) findViewById(R.id.hlf_txvUserName);
        mTxvUserPassword = (TextView) findViewById(R.id.hlf_txvUserPassword);
        mIvImageProfile = (ImageView) findViewById(R.id.hlf_ivProfilePicture);

        fill();
    }
    private void fill()
    {
        mEmail = getIntent().getStringExtra("email");
        mUser = Users_Singleton.getUsers_Singleton().getUserByEmail(mEmail);

        mIvImageProfile.setImageResource( mUser.getProfilePicture());
        mTxvUserName.setText(mUser.getNick());
        mTxvUserPassword.setText(mUser.getPassword());
    }
}
