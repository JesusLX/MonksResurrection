package com.limox.jesus.monksresurrection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.HelpLogin.HelpLoginFinal_Fragment;
import com.limox.jesus.monksresurrection.Fragments.HelpLogin.HelpLoginPassword_Fragment;
import com.limox.jesus.monksresurrection.Fragments.HelpLogin.StartHelpLogin_Fragment;
import com.limox.jesus.monksresurrection.Interfaces.HomeOfFragments;

public class HelpLogin_Activity extends AppCompatActivity implements HomeOfFragments, StartHelpLogin_Fragment.OnStartHelpLoginFragmentListener, HelpLoginPassword_Fragment.OnHelpLoginPasswordFragmentListener {
    Fragment mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_login);
        if (savedInstanceState == null){
            startHelpLogin();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Start the fragment StartSignUp_Fragmetn, adding it to the backStack
     */
    public void startHelpLogin(){
        startFragment(new StartHelpLogin_Fragment(),true);
    }

    @Override
    public void startHelpLoginFinalFragment(Bundle args) {
        startFragment(HelpLoginFinal_Fragment.newInstance(args),false);
    }

    @Override
    public void startHelpLoginPasswordFragment() {
       startFragment(new HelpLoginPassword_Fragment(),true);

    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(R.id.activity_help_login, mCurrentFragment);
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
