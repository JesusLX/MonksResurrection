package com.limox.jesus.monksresurrection;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Fragments.SignUp.SignUpEmail_Fragment;
import com.limox.jesus.monksresurrection.Fragments.SignUp.StartSignUp_Fragment;

public class SignUp_Activity extends AppCompatActivity implements StartSignUp_Fragment.StartSignUpLoginListener{
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
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_sign_up,startSignUpFragment).commit();
    }
    @Override
    public void startSignUpEmail() {
        signUpEmailFragment = new SignUpEmail_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_sign_up,signUpEmailFragment).commit();
    }

    @Override
    public void startOtherSitesPlatform(int idPlaform) {

    }

}
