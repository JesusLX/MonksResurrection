package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.SignUp.SignUpEmail_Fragment;
import com.limox.jesus.monksresurrection.Fragments.SignUp.SignUpUser_Fragment;
import com.limox.jesus.monksresurrection.Fragments.SignUp.StartSignUp_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;

public class SignUp_Activity extends AppCompatActivity implements HomeOfFragments, StartSignUp_Fragment.StartSignUpLoginFragmentListener, SignUpEmail_Fragment.SignUpEmailListener, SignUpUser_Fragment.OnSignUpUserFragmentListener {

    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        startSignUp();

    }

    public void startSignUp() {
        startFragment(new StartSignUp_Fragment(), true);
    }

    @Override
    public void startSignUpEmail() {
        startFragment(new SignUpEmail_Fragment(), true);

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
        startFragment(SignUpUser_Fragment.newInstance(args), false);
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(SignUp_Activity.this, Home_Activity.class));
        finish();
    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_sign_up, mCurrentFragment);
        ft.commit();
    }
}
