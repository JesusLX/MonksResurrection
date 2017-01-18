package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Utils.Preferences;

public class Start_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (Preferences.hasACurrentUser(Start_Activity.this)){
            startHome();
        }else
            startLogin();
    }

    private void startHome() {
        startActivity(new Intent(Start_Activity.this,Home_Activity.class));
    }

    private void startLogin() {
        startActivity(new Intent(Start_Activity.this,Login_Activity.class));
    }
}
