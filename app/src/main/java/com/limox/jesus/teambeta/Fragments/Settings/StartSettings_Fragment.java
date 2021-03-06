package com.limox.jesus.teambeta.Fragments.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.limox.jesus.teambeta.R;
import com.limox.jesus.teambeta.Repositories.Users_Repository;
import com.limox.jesus.teambeta.Utils.Preferences;
import com.limox.jesus.teambeta.Utils.UIUtils;


public class StartSettings_Fragment extends Fragment {

    private OnStartSettingsListener mCallback;
    private View.OnClickListener mOnClickListener;

    private ImageView ivwBack;

    private TextView txvEdtProfile;
    private TextView txvResetPassord;
    private TextView txvNotifications;
    private TextView txvTBHelperCenter;
    private TextView txvReportProblem;
    private TextView txvLogOut;
    private TextView txvAboutUs;
    private Toolbar mToolbar;
    private Switch sNotifications;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start_settings, container, false);
        ivwBack = (ImageView) rootView.findViewById(R.id.ss_btnBack);

        txvEdtProfile = (TextView) rootView.findViewById(R.id.ss_txvEdtProf);
        txvResetPassord = (TextView) rootView.findViewById(R.id.ss_txvResetePass);
        txvNotifications = (TextView) rootView.findViewById(R.id.ss_txvNotifications);
        txvTBHelperCenter = (TextView) rootView.findViewById(R.id.ss_txvTBHelper);
        txvReportProblem = (TextView) rootView.findViewById(R.id.ss_txvReportProblem);
        txvAboutUs = (TextView) rootView.findViewById(R.id.ss_txvAboutUs);
        txvLogOut = (TextView) rootView.findViewById(R.id.ss_txvLogOut);
        sNotifications = (Switch) rootView.findViewById(R.id.ss_sNotifications);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializateOnClickListener();

        ivwBack.setOnClickListener(mOnClickListener);
        txvEdtProfile.setOnClickListener(mOnClickListener);
        txvResetPassord.setOnClickListener(mOnClickListener);
        txvNotifications.setOnClickListener(mOnClickListener);
        txvTBHelperCenter.setOnClickListener(mOnClickListener);
        txvReportProblem.setOnClickListener(mOnClickListener);
        txvAboutUs.setOnClickListener(mOnClickListener);
        txvLogOut.setOnClickListener(mOnClickListener);
        sNotifications.setChecked(Preferences.getNotifications(getContext()));
        sNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Preferences.activateNotifications(getContext());
                } else
                    Preferences.removeNotifications(getContext());
            }
        });
        if (Users_Repository.get().getCurrentForum() != null)
            mToolbar.setBackgroundColor(UIUtils.parseColor(Users_Repository.get().getCurrentForum().getColor()));


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
                        mCallback.startChangePasswordFragment();
                        break;
                    case R.id.ss_txvNotifications:
                        mCallback.startNotificationsFragment();
                        break;
                    case R.id.ss_txvTBHelper:
                        mCallback.startTeamBetasHelpCenter();
                        break;
                    case R.id.ss_txvReportProblem:
                        mCallback.startReportProblemFragmentDialog();
                        break;
                    case R.id.ss_txvAboutUs:
                        mCallback.startAboutUs();
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnStartSettingsListener) {
            mCallback = (OnStartSettingsListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
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

        void startChangePasswordFragment();

        void startPostsLikedFragment();

        void startNotificationsFragment();

        void startTeamBetasHelpCenter();

        void startReportProblemFragmentDialog();

        void logOut();

        void startAboutUs();
    }


}
