package com.limox.jesus.monksresurrection.Fragments.Settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.limox.jesus.monksresurrection.R;


public class StartSettings_Fragment extends Fragment {

    private OnStartSettingsListener mCallback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_settings, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartSettingsListener) {
            mCallback = (OnStartSettingsListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnStartSettingsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        
    }

    public interface OnStartSettingsListener {

    }
}
