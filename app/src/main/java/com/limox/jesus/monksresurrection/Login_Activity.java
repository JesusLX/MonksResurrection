package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class Login_Activity extends AppCompatActivity {

    TextView mTxvSignUp;
    TextView mTxvFP;
    Button mBtnLogIn;
    AdapterView.OnClickListener mCliclListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTxvSignUp = (TextView) findViewById(R.id.lgn_txvSignUp);
        mTxvFP = (TextView) findViewById(R.id.lgn_txvFP);
        mBtnLogIn = (Button) findViewById(R.id.lgn_btnSignIn);

        mCliclListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.lgn_txvFP:
                        startActivity(new Intent(Login_Activity.this,HelpLogin_Activity.class));
                        break;
                    case R.id.lgn_txvSignUp:
                        startActivity(new Intent(Login_Activity.this,StartSignUp_Activity.class));
                        break;
                    case R.id.lgn_btnSignIn:
                        startActivity(new Intent(Login_Activity.this,FixedPostsList_Activity.class));
                        break;
                }
            }
        };

        mTxvFP.setOnClickListener(mCliclListener);
        mTxvSignUp.setOnClickListener(mCliclListener);
        mBtnLogIn.setOnClickListener(mCliclListener);
    }


}
