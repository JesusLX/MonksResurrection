package com.limox.jesus.monksresurrection;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.Settings.StartSettings_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;

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
}
