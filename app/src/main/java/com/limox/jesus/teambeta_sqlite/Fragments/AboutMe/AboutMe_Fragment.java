package com.limox.jesus.teambeta_sqlite.Fragments.AboutMe;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.teambeta_sqlite.R;

public class AboutMe_Fragment extends Fragment {

    public static AboutMe_Fragment newInstance(){
        AboutMe_Fragment mFragment = new AboutMe_Fragment();
        return mFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_about_me, container,
                false);

        return rootView;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about_me);
    }*/
}
