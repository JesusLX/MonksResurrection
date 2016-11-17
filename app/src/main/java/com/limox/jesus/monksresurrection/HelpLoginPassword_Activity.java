package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.limox.jesus.monksresurrection.Model.User;
import com.limox.jesus.monksresurrection.Singleton.Users_Singleton;

public class HelpLoginPassword_Activity extends AppCompatActivity {

    EditText mEdtEmail;
    Button mBtnNext;
    String mEmail;
    View.OnClickListener mListenerOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_login_password);
        // Inicializate
        mEdtEmail = (EditText) findViewById(R.id.hlp_edtEmail);
        mBtnNext = (Button) findViewById(R.id.hlp_btnSend);
        createOnClick();
        mBtnNext.setOnClickListener(mListenerOnClick);


    }

    void createOnClick(){
        mListenerOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email introducced
                mEmail = mEdtEmail.getText().toString();
                User tmpUer;
                if ((tmpUer = Users_Singleton.get().getUserByEmail(mEmail)) != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("email",mEmail);
                    Intent intent = new Intent(HelpLoginPassword_Activity.this,HelpLoginFinal_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    mEdtEmail.setError(getString(R.string.message_error_email_not_registred));
                }

            }
        };
    }
}
