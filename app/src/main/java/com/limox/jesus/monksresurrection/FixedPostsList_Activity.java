package com.limox.jesus.monksresurrection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.limox.jesus.monksresurrection.Adapters.PostAdapterRecycler;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class FixedPostsList_Activity extends AppCompatActivity {

    RecyclerView mRV;
    PostAdapterRecycler PARAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_posts_list);

        mRV = (RecyclerView) findViewById(R.id.fpl_rvPostsFixed);

        PARAdapter = new PostAdapterRecycler(this, AllConstants.FOR_NONPUBLISHED);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(PARAdapter);
    }
}
