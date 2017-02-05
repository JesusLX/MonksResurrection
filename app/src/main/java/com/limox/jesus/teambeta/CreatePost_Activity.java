package com.limox.jesus.teambeta;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.limox.jesus.teambeta.Repositories.Posts_Repository;

public class CreatePost_Activity extends AppCompatActivity {
    EditText mEdtTitle;
    EditText mEdtDescription;
    EditText mEdtTags;
    String mTitle;
    String mDescriptions;
    Toolbar mToolbar;
    String mTags;
    RelativeLayout mRlContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.limox.jesus.teambeta.R.layout.activity_create_post);
        mEdtTitle = (EditText) findViewById(com.limox.jesus.teambeta.R.id.crp_EdtPostTitle);
        mEdtDescription = (EditText) findViewById(com.limox.jesus.teambeta.R.id.crp_EdtPostDescription);
        mEdtTags = (EditText) findViewById(com.limox.jesus.teambeta.R.id.crp_EdtPostTags);
        mToolbar = (Toolbar) findViewById(com.limox.jesus.teambeta.R.id.acp_toolbar);
        mRlContainer = (RelativeLayout) findViewById(com.limox.jesus.teambeta.R.id.activity_create_post);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(com.limox.jesus.teambeta.R.string.create_post));
        getSupportActionBar().setHomeAsUpIndicator(com.limox.jesus.teambeta.R.drawable.ic_action_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(com.limox.jesus.teambeta.R.menu.menu_create_post, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case com.limox.jesus.teambeta.R.id.action_send:
                sendPost();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void sendPost() {
        if (validate()){
            //TODO Cambiar esto, ahora crea directamente uno publicado
            Posts_Repository.get().createPost(mTitle,mDescriptions,mTags);
            finish();
        }else
            Snackbar.make(mRlContainer, com.limox.jesus.teambeta.R.string.message_error_must_fill,Snackbar.LENGTH_LONG).show();

    }
}
