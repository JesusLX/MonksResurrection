package com.limox.jesus.monksresurrection;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.limox.jesus.monksresurrection.Adapters.StartButtonsAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Index_Fragment extends Fragment {

    StartButtonsAdapter mAdapter;
    ListView mLvStartButtons;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> starButtons = new ArrayList<>();
        starButtons.add("Dash index");
        starButtons.add("Settings");
        starButtons.add("Acerca de");
        mAdapter = new StartButtonsAdapter(getContext(),starButtons);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_index,container);
        mLvStartButtons = (ListView) rootView.findViewById(R.id.i_lvStartButtons);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvStartButtons.setAdapter(mAdapter);

    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


    }*/
}
