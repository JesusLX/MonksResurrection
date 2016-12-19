package com.limox.jesus.monksresurrection;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.HelpLogin.HelpLoginFinal_Fragment;
import com.limox.jesus.monksresurrection.Fragments.HelpLogin.HelpLoginPassword_Fragment;
import com.limox.jesus.monksresurrection.Fragments.HelpLogin.StartHelpLogin_Fragment;

public class HelpLogin_Activity extends AppCompatActivity implements StartHelpLogin_Fragment.OnStartHelpLoginFragmentListener, HelpLoginPassword_Fragment.OnHelpLoginPasswordFragmentListener {

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
        StartHelpLogin_Fragment shlf = new StartHelpLogin_Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_help_login,shlf).commit();
    }

    @Override
    public void startHelpLoginFinalFragment(Bundle args) {
        HelpLoginFinal_Fragment hlff = HelpLoginFinal_Fragment.newInstance(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_sign_up,hlff).commit();
    }

    @Override
    public void startHelpLoginPasswordFragment() {
        HelpLoginPassword_Fragment hlpf = new HelpLoginPassword_Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_help_login,hlpf).commit();
    }
}
