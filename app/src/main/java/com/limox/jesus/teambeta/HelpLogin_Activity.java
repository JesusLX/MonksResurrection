package com.limox.jesus.teambeta;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.teambeta.Fragments.HelpLogin.HelpLoginFinal_Fragment;
import com.limox.jesus.teambeta.Fragments.HelpLogin.HelpLoginPassword_Fragment;
import com.limox.jesus.teambeta.Fragments.HelpLogin.StartHelpLogin_Fragment;
import com.limox.jesus.teambeta.Interfaces.HomeOfFragments;
import com.limox.jesus.teambeta.Utils.AllConstants;

public class HelpLogin_Activity extends AppCompatActivity implements HomeOfFragments, StartHelpLogin_Fragment.OnStartHelpLoginFragmentListener, HelpLoginPassword_Fragment.OnHelpLoginPasswordFragmentListener {
    Fragment mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TeamBetaApplication.setCurrentActivity("HelpLogin_Activity");
        setContentView(com.limox.jesus.teambeta.R.layout.activity_help_login);
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
        startFragment(new StartHelpLogin_Fragment(),true, AllConstants.FragmentTag.HelpLoginTag);
    }

    @Override
    public void startHelpLoginFinalFragment(Bundle args) {
        startFragment(HelpLoginFinal_Fragment.newInstance(args),false, AllConstants.FragmentTag.HelpLoginFinalTag);
    }

    @Override
    public void startHelpLoginPasswordFragment() {
       startFragment(new HelpLoginPassword_Fragment(),true, AllConstants.FragmentTag.HelpLoginPassTag);

    }

    @Override
    public void startFragment(Fragment fragment, boolean addStack, String tag) {
        mCurrentFragment = fragment;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addStack)
            ft.addToBackStack(null);
        ft.replace(com.limox.jesus.teambeta.R.id.activity_help_login, mCurrentFragment,tag);
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
