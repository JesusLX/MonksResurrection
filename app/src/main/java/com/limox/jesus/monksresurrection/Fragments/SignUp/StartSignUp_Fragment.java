package com.limox.jesus.monksresurrection.Fragments.SignUp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.R;
import com.limox.jesus.monksresurrection.Utils.AllConstants;

public class StartSignUp_Fragment extends Fragment {

    TextView mTxvSignUp;
    AdapterView.OnClickListener mClickListener;
    StartSignUpLoginListener mCallback;
    View.OnClickListener mOtherPlatformOnClickListener;

    public interface StartSignUpLoginListener {
        void startSignUpEmail();

        void startOtherSitesPlatform(int idPlaform);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (StartSignUpLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() + " must implement StartSignUp_Fragment");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_start_sign_up, container);

        mTxvSignUp = (TextView) rootView.findViewById(R.id.ssu_txvSignUp);

        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ssu_txvSignUp:
                        mCallback.startSignUpEmail();
                        break;
                }
            }
        };

        mTxvSignUp.setOnClickListener(mClickListener);

        mOtherPlatformOnClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.ssu_btnGg:
                        mCallback.startOtherSitesPlatform(AllConstants.PLATFORM_GOOGLE);
                        break;
                    case R.id.ssu_btnFb:
                        mCallback.startOtherSitesPlatform(AllConstants.PLATFORM_FACEBOOK);
                        break;
                    case R.id.ssu_btnStm:
                        mCallback.startOtherSitesPlatform(AllConstants.PLATFORM_STEAM);
                        break;
                }
            }
        };
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
