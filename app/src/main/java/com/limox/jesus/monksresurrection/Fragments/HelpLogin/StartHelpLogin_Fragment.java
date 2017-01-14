package com.limox.jesus.monksresurrection.Fragments.HelpLogin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.R;

public class StartHelpLogin_Fragment extends Fragment {

    TextView mTxvToHelpEmail;
    OnStartHelpLoginFragmentListener mCallback;

    public interface OnStartHelpLoginFragmentListener{
        void startHelpLoginPasswordFragment();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnStartHelpLoginFragmentListener)
            mCallback = (OnStartHelpLoginFragmentListener) activity;
        else {
            throw new ClassCastException(activity.toString() + " must implements OnStartHelpLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_start_help_login,null,false);
        mTxvToHelpEmail = (TextView) rootView.findViewById(R.id.hl_txvRecoverWithEmail);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTxvToHelpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startHelpLoginPasswordFragment();
            }
        });
    }
}
