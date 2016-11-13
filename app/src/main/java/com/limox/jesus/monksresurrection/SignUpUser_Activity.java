package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;
import com.limox.jesus.monksresurrection.Validators.Validate;

public class SignUpUser_Activity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    EditText edtRepeatPassword;
    Button btnCreateAcount;

    String email;
    String name;
    String password;
    String repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        email = getIntent().getStringExtra("email");
        edtUserName = (EditText) findViewById(R.id.suu_edtName);
        edtPassword = (EditText) findViewById(R.id.suu_edtPassword);
        edtRepeatPassword = (EditText) findViewById(R.id.suu_edtRpPassword);
        btnCreateAcount = (Button) findViewById(R.id.suu_btnCreate);



        btnCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateParams()){
                    // if all right create user and use it like current user of the app
                    Users_Singleton.getUsers_Singleton().addUser(name,password,email);
                    Users_Singleton.getUsers_Singleton().setCurrentUser(Users_Singleton.getUsers_Singleton().getUser(name));
                    //TODO añadir que se ponga aqui el usuario en los settings
                    startActivity(new Intent(SignUpUser_Activity.this,Index_Activity.class));
                    finish();
                }
            }
        });

    }
    private boolean validateParams(){
        boolean allRigth = true;

        name = edtUserName.getText().toString();
        password = edtPassword.getText().toString();
        repeatPassword = edtRepeatPassword.getText().toString();

        if (Validate.validateName(name) != Validate.MESSAGE_OK){
            edtUserName.setError(getResources().getString(Validate.validateName(name)));
            allRigth = false;
        }
        if (Validate.validatePassword(password) != Validate.MESSAGE_OK){
            edtPassword.setError(getResources().getString(Validate.validatePassword(password)));
            allRigth = false;
        }
        if (Validate.validateRepeatedPassword(password,repeatPassword) != Validate.MESSAGE_OK){
            edtRepeatPassword.setError(getResources().getString(Validate.validateRepeatedPassword(password,repeatPassword)));
            allRigth = false;
        }

        return allRigth;
    }
}
