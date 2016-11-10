package com.limox.jesus.monksresurrection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limox.jesus.monksresurrection.Model.Post;

public class PostView_Activity extends AppCompatActivity {

    Post mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
    }
}
