package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpLogin_Activity extends AppCompatActivity {

    TextView mTxvToHelpEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_login);

        mTxvToHelpEmail = (TextView) findViewById(R.id.hl_txvRecoverUser);
        mTxvToHelpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpLogin_Activity.this,HelpLoginPassword_Activity.class));
                //TODO Ahora poner en la singleton de usuario un get usuario by emaily seguir con la siguiente activity
            }
        });
    }
}
