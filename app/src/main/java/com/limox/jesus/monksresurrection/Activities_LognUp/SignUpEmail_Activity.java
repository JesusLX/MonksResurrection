package com.limox.jesus.monksresurrection.Activities_LognUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.R;

import org.w3c.dom.Text;


public class SignUpEmail_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);
    }

   public void onClickToUser(View view){
       startActivity(new Intent(SignUpEmail_Activity.this,SignUpUser_Activity.class));
   }
}
