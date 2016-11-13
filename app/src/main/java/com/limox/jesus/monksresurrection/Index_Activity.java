package com.limox.jesus.monksresurrection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.limox.jesus.monksresurrection.Adapters.StartButtonsAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Index_Activity extends AppCompatActivity {

    StartButtonsAdapter mAdapter;
    ListView mLvStartButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ArrayList<String> starButtons = new ArrayList<>();
        starButtons.add("Dash index");
        starButtons.add("Settings");
        mAdapter = new StartButtonsAdapter(this,starButtons);
        mLvStartButtons = (ListView) findViewById(R.id.i_lvStartButtons);
        mLvStartButtons.setAdapter(mAdapter);
    }
}
