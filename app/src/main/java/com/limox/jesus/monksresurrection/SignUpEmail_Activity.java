package com.limox.jesus.monksresurrection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Validators.Validate;


public class SignUpEmail_Activity extends AppCompatActivity {



    EditText edtEmail;
    Button btnValidate;
    TextView txvSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        edtEmail = (EditText) findViewById(R.id.sue_edtEmail);
        btnValidate = (Button) findViewById(R.id.sue_btnNext);
        txvSignIn = (TextView) findViewById(R.id.sue_txvSignIn);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int message = Validate.validateEmail(edtEmail.getText().toString());
                if ( message == Validate.MESSAGE_OK){
                    Bundle bundle = new Bundle();
                    bundle.putString("email",edtEmail.getText().toString());
                    Intent intent = new Intent(SignUpEmail_Activity.this,SignUpUser_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else
                    edtEmail.setError(getResources().getString(Validate.validateEmail(edtEmail.getText().toString())));
            }
        });
        txvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpEmail_Activity.this,Login_Activity.class));
                finish();
            }
        });
    }
}
