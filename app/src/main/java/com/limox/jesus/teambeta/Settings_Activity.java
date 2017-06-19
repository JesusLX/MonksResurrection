package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.UserProfile.ChangePassword_Fragment;
import com.limox.jesus.teambeta.Fragments.Settings.StartSettings_Fragment;
import com.limox.jesus.teambeta.Fragments.UserProfile.EditUser_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.AllConstants;
import com.limox.jesus.teambeta.Utils.Preferences;

public class Settings_Activity extends AppCompatActivity implements HomeOfFragments, StartSettings_Fragment.OnStartSettingsListener{
    Fragment mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TeamBetaApplication.setCurrentActivity("Settings_Activity");
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null){
            startStartSettingsFragment();
        }
    }

    public void startStartSettingsFragment(){
        startFragment(new StartSettings_Fragment(),false, AllConstants.FragmentTag.SettingsTag);
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack,String tag) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_settings, mCurrentFragment, tag);
        ft.commit();
    }

    @Override
    public void startEditProfileFragment() {
        startFragment(EditUser_Fragment.newInstance(Users_Repository.get().getCurrentUser().optBundle()), false, AllConstants.FragmentTag.EditUser);
    }

    @Override
    public void startChangePasswordFragment() {
        startFragment(ChangePassword_Fragment.newInstance(Users_Repository.get().getCurrentUser().optBundle()), false, AllConstants.FragmentTag.EditUser);
    }

    @Override
    public void startPostsLikedFragment() {

    }

    @Override
    public void startNotificationsFragment() {

    }



    @Override
    public void startMonksHelpCenter() {

    }

    @Override
    public void startReportProblemFragmentDialog() {

    }

    @Override
    public void logOut() {

        Preferences.removeCurrentUser(Settings_Activity.this);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);

    }
}
