package com.limox.jesus.teambeta;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.SignUp.SignUpEmail_Fragment;
import com.limox.jesus.teambeta.Fragments.SignUp.SignUpUser_Fragment;
import com.limox.jesus.teambeta.Fragments.SignUp.StartSignUp_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class SignUp_Activity extends AppCompatActivity implements HomeOfFragments, StartSignUp_Fragment.StartSignUpLoginFragmentListener, SignUpEmail_Fragment.SignUpEmailListener, SignUpUser_Fragment.OnSignUpUserFragmentListener {

    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_sign_up);

        startSignUpEmail();

    }

    public void startSignUp() {
        startFragment(new StartSignUp_Fragment(), true, AllConstants.FragmentTag.SignUpTag);
    }

    @Override
    public void startSignUpEmail() {
        startFragment(new SignUpEmail_Fragment(), true, AllConstants.FragmentTag.SignUpEmailTag);

    }

    @Override
    public void startOtherSitesPlatform(int idPlaform) {

    }

    @Override
    public void backToLogin() {
        startActivity(new Intent(SignUp_Activity.this, Login_Activity.class));
        finish();
    }

    @Override
    public void startSignUpUser(Bundle args) {
        startFragment(SignUpUser_Fragment.newInstance(args), false, AllConstants.FragmentTag.SignUpUserTag);
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(SignUp_Activity.this, Home_Activity.class));
        finish();
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack,String tag) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.activity_sign_up, mCurrentFragment,tag);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()<= 1)
            finish();
        else
            super.onBackPressed();
    }
}
