package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.Settings.Settings.LanguageFragment;
import com.limox.jesus.monksresurrection.Fragments.Settings.StartSettings_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;
import com.limox.jesus.monksresurrection.Utils.Preferences;

public class Settings_Activity extends AppCompatActivity implements HomeOfFragments, StartSettings_Fragment.OnStartSettingsListener{
    Fragment mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null){
            startStartSettingsFragment();
        }
    }

    public void startStartSettingsFragment(){
        startFragment(new StartSettings_Fragment(),false);
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_settings, mCurrentFragment);
        ft.commit();
    }

    @Override
    public void startEditProfileFragment() {

    }

    @Override
    public void startResetPasswordFragment() {

    }

    @Override
    public void startPostsLikedFragment() {

    }

    @Override
    public void startNotificationsFragment() {

    }

    @Override
    public void startLanguageFragment() {
        startFragment(new LanguageFragment(),false);
    }

    @Override
    public void startMonksHelpCenter() {

    }

    @Override
    public void startReportProblemFragmentDialog() {

    }

    @Override
    public void logOut() {

        Preferences.removeCurrentUser(this);
        Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
