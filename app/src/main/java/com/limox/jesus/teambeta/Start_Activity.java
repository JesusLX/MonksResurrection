package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Utils.Preferences;

public class Start_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_start);
        if (Preferences.hasACurrentUser(Start_Activity.this)){
            startHome();
        }else
            startLogin();
        finish();
    }

    private void startHome() {
        startActivity(new Intent(Start_Activity.this,Home_Activity.class));
    }

    private void startLogin() {
        startActivity(new Intent(Start_Activity.this,Login_Activity.class));
    }
}
