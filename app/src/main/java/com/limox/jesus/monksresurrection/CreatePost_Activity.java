package com.limox.jesus.monksresurrection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.limox.jesus.monksresurrection.Repositories.Posts_Repository;

public class CreatePost_Activity extends AppCompatActivity {
    EditText mEdtTitle;
    EditText mEdtDescription;
    EditText mEdtTags;
    Button mBtnCrear;
    String mTitle;
    String mDescriptions;
    String mTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        mEdtTitle = (EditText) findViewById(R.id.crp_EdtPostTitle);
        mEdtDescription = (EditText) findViewById(R.id.crp_EdtPostDescription);
        mEdtTags = (EditText) findViewById(R.id.crp_EdtPostTags);
        mBtnCrear = (Button) findViewById(R.id.crp_btnEnviar);



        mBtnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    //TODO Cambiar esto, ahora crea directamente uno publicado
                    Posts_Repository.get().createPostPublished(mTitle,mDescriptions,mTags);
                    finish();
                }

            }
        });

    }

    private boolean validate() {
        boolean allRigth = false;

        mTitle = mEdtTitle.getText().toString();
        mDescriptions= mEdtDescription.getText().toString();
        mTags = mEdtTags.getText().toString();

        if (mTitle.length() > 0 && mDescriptions.length() > 0 && mTags.length() > 0)
            allRigth = true;
        return allRigth;
    }
}
