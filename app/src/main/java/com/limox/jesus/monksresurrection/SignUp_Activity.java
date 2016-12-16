package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.SignUp.SignUpEmail_Fragment;
import com.limox.jesus.monksresurrection.Fragments.SignUp.SignUpUser_Fragment;
import com.limox.jesus.monksresurrection.Fragments.SignUp.StartSignUp_Fragment;

public class SignUp_Activity extends AppCompatActivity implements StartSignUp_Fragment.StartSignUpLoginListener, SignUpEmail_Fragment.SignUpEmailListener,SignUpUser_Fragment.OnSignUpUserFragmentListener{
    private SignUpEmail_Fragment signUpEmailFragment;
    private StartSignUp_Fragment startSignUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        startSignUp();

    }

    public void startSignUp() {
        startSignUpFragment = new StartSignUp_Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_sign_up,startSignUpFragment).commit();
    }
    @Override
    public void startSignUpEmail() {
        signUpEmailFragment = new SignUpEmail_Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_sign_up,signUpEmailFragment).commit();
    }

    @Override
    public void startOtherSitesPlatform(int idPlaform) {

    }

    @Override
    public void backToLogin() {
        startActivity(new Intent(SignUp_Activity.this,Login_Activity.class));
        finish();
    }

    @Override
    public void startSignUpUser(Bundle args) {
        SignUpUser_Fragment suu = SignUpUser_Fragment.newInstance(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_sign_up,suu).commit();
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(SignUp_Activity.this,Home_Activity.class));
        finish();
    }
}
