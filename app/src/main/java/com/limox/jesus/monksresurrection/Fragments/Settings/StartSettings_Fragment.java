package com.limox.jesus.monksresurrection.Fragments.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.limox.jesus.monksresurrection.R;


public class StartSettings_Fragment extends Fragment {

    private OnStartSettingsListener mCallback;
    private View.OnClickListener mOnClickListener;

    private ImageView ivwBack;

    private TextView txvEdtProfile;
    private TextView txvResetPassord;
    private TextView txvPostsLiked;
    private TextView txvNotifications;
    private TextView txvLanguage;
    private TextView txvMonskHelperCenter;
    private TextView txvReportProblem;
    private TextView txvLogOut;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start_settings, container, false);
        ivwBack = (ImageView) rootView.findViewById(R.id.ss_btnBack);

        txvEdtProfile = (TextView) rootView.findViewById(R.id.ss_txvEdtProf);
        txvResetPassord = (TextView) rootView.findViewById(R.id.ss_txvResetePass);
        txvPostsLiked = (TextView) rootView.findViewById(R.id.ss_txvPostLiked);
        txvNotifications = (TextView) rootView.findViewById(R.id.ss_txvNotifications);
        txvLanguage = (TextView) rootView.findViewById(R.id.ss_txvLanguage);
        txvMonskHelperCenter = (TextView) rootView.findViewById(R.id.ss_txvMonkHelper);
        txvReportProblem = (TextView) rootView.findViewById(R.id.ss_txvReportProblem);
        txvLogOut = (TextView) rootView.findViewById(R.id.ss_txvLogOut);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializateOnClickListener();

        ivwBack.setOnClickListener(mOnClickListener);
        txvEdtProfile.setOnClickListener(mOnClickListener);
        txvResetPassord.setOnClickListener(mOnClickListener);
        txvPostsLiked.setOnClickListener(mOnClickListener);
        txvNotifications.setOnClickListener(mOnClickListener);
        txvLanguage.setOnClickListener(mOnClickListener);
        txvMonskHelperCenter.setOnClickListener(mOnClickListener);
        txvReportProblem.setOnClickListener(mOnClickListener);
        txvLogOut.setOnClickListener(mOnClickListener);

    }

    private void initializateOnClickListener() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ss_txvEdtProf:
                        mCallback.startEditProfileFragment();
                        break;
                    case R.id.ss_txvResetePass:
                        mCallback.startResetPasswordFragment();
                        break;
                    case R.id.ss_txvPostLiked:
                        mCallback.startPostsLikedFragment();
                        break;
                    case R.id.ss_txvNotifications:
                        mCallback.startNotificationsFragment();
                        break;
                    case R.id.ss_txvLanguage:
                        mCallback.startLanguageFragment();
                        break;
                    case R.id.ss_txvMonkHelper:
                        mCallback.startMonksHelpCenter();
                        break;
                    case R.id.ss_txvReportProblem:
                        mCallback.startReportProblemFragmentDialog();
                        break;
                    case R.id.ss_txvLogOut:
                        mCallback.logOut();
                        break;
                    case R.id.ss_btnBack:
                        getActivity().onBackPressed();
                        break;

                }
            }
        };
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

        void startEditProfileFragment();
        void startResetPasswordFragment();
        void startPostsLikedFragment();

        void startNotificationsFragment();
        void startLanguageFragment();

        void startMonksHelpCenter();
        void startReportProblemFragmentDialog();

        void logOut();
    }




}
